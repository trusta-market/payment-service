package com.trustamarket.paymentservice.paymentservice.payment.application.service;

import com.trustamarket.paymentservice.paymentservice.payment.application.dto.command.CreatePaymentCommand;
import com.trustamarket.paymentservice.paymentservice.payment.application.dto.command.FailPaymentCommand;
import com.trustamarket.paymentservice.paymentservice.payment.application.dto.command.SucceededPaymentCommand;
import com.trustamarket.paymentservice.paymentservice.payment.application.dto.query.PaymentDetailQuery;
import com.trustamarket.paymentservice.paymentservice.payment.application.dto.query.PaymentSearchQuery;
import com.trustamarket.paymentservice.paymentservice.payment.application.dto.result.CreatePaymentResult;
import com.trustamarket.paymentservice.paymentservice.payment.application.dto.result.FailPaymentResult;
import com.trustamarket.paymentservice.paymentservice.payment.application.dto.result.PaymentDetailResult;
import com.trustamarket.paymentservice.paymentservice.payment.application.dto.result.PaymentInfoResult;
import com.trustamarket.paymentservice.paymentservice.payment.application.dto.result.SearchPaymentResult;
import com.trustamarket.paymentservice.paymentservice.payment.application.dto.result.SucceededPaymentResult;
import com.trustamarket.paymentservice.paymentservice.payment.application.port.PaymentResponseResult;
import com.trustamarket.paymentservice.paymentservice.payment.application.port.PaymentUseCase;
import com.trustamarket.paymentservice.paymentservice.payment.application.port.TossPaymentPort;
import com.trustamarket.paymentservice.paymentservice.payment.application.port.WalletPort;
import com.trustamarket.paymentservice.paymentservice.payment.domain.entity.Payment;
import com.trustamarket.paymentservice.paymentservice.payment.domain.exception.PaymentErrorCode;
import com.trustamarket.paymentservice.paymentservice.payment.domain.exception.PaymentException;
import com.trustamarket.paymentservice.paymentservice.payment.domain.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentService implements PaymentUseCase {

    private final PaymentRepository paymentRepository;
    private final TossPaymentPort tossPaymentPort;
    private final WalletPort walletPort;

    @Override
    @Transactional
    public CreatePaymentResult createPayment(CreatePaymentCommand command) {
        try{
            Payment payment = Payment.create(command.userId(), command.pointTxRequestHistoryId(), command.amount());
            Payment savedPayment = paymentRepository.saveAndFlush(payment);

            CreatePaymentResult result = CreatePaymentResult.from(savedPayment);
            return result;

        }catch (DataIntegrityViolationException e){
            throw new PaymentException(PaymentErrorCode.DUPLICATE_CHARGE_ID);
        }
    }

    @Override
    @Transactional
    public SucceededPaymentResult succeededPayment(SucceededPaymentCommand command) {
        Payment payment = paymentRepository.findById(command.paymentId());
        payment.validateConfirm(command.paymentKey(), command.amount());

        try {
            tossPaymentPort.confirm(
                    command.paymentKey(),
                    command.paymentId(),
                    command.amount()
            );

            payment.successPayment(command.paymentKey(), command.amount());
            SucceededPaymentResult frontResult = SucceededPaymentResult.from(payment);
            PaymentResponseResult result = PaymentResponseResult.from(payment);

            try {
                walletPort.pointToWallet(result);
            } catch (Exception e){
                // todo : 포인트 적립 실패 로직
            }

            return frontResult;

        } catch (PaymentException e) {
            FailPaymentCommand failCommand = new FailPaymentCommand(
                    command.paymentId(),
                    "CONFIRM_FAIL",
                    e.getMessage()
            );
            failPayment(failCommand);
            throw e;
        }
    }

    @Override
    @Transactional
    public FailPaymentResult failPayment(FailPaymentCommand command) {
        Payment payment = paymentRepository.findById(command.paymentId());

        payment.failPayment(command.code(), command.message());

        FailPaymentResult result = FailPaymentResult.from(payment);
        walletPort.pointToWallet(PaymentResponseResult.from(payment));
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public PaymentDetailResult getPaymentDetail(PaymentDetailQuery command) {
        Payment payment = paymentRepository.findById(command.paymentId());
        if(!payment.getUserId().equals(command.userId())) {
            throw new PaymentException(PaymentErrorCode.PAYMENT_ACCESS_DENIED);
        }

        PaymentDetailResult result = PaymentDetailResult.from(payment);

        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public Slice<SearchPaymentResult> searchPayments(PaymentSearchQuery query, Pageable pageable) {
        Slice<Payment> payments = paymentRepository.searchPayments(query, pageable);
        return payments.map(SearchPaymentResult::from);
    }

    @Override
    @Transactional(readOnly = true)
    public PaymentInfoResult getPaymentInfo(UUID paymentId) {
        Payment payment = paymentRepository.findById(paymentId);
        PaymentInfoResult result = new PaymentInfoResult(payment.getPaymentId(), payment.getAmount());
        return result;
    }
}
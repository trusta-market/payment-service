package com.trustamarket.paymentservice.paymentservice.application.service;

import com.trustamarket.paymentservice.paymentservice.application.dto.command.CreatePaymentCommand;
import com.trustamarket.paymentservice.paymentservice.application.dto.command.FailPaymentCommand;
import com.trustamarket.paymentservice.paymentservice.application.dto.command.SucceededPaymentCommand;
import com.trustamarket.paymentservice.paymentservice.application.dto.result.CreatePaymentResult;
import com.trustamarket.paymentservice.paymentservice.application.dto.result.FailPaymentResult;
import com.trustamarket.paymentservice.paymentservice.application.dto.result.SucceededPaymentResult;
import com.trustamarket.paymentservice.paymentservice.application.port.PaymentUseCase;
import com.trustamarket.paymentservice.paymentservice.domain.entity.Payment;
import com.trustamarket.paymentservice.paymentservice.domain.exception.PaymentErrorCode;
import com.trustamarket.paymentservice.paymentservice.domain.exception.PaymentException;
import com.trustamarket.paymentservice.paymentservice.domain.repository.PaymentRepository;
import com.trustamarket.paymentservice.paymentservice.domain.vo.Amount;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PaymentService implements PaymentUseCase {

    private final PaymentRepository paymentRepository;

    @Override
    @Transactional
    public CreatePaymentResult createPayment(CreatePaymentCommand command) {
        try{
            Payment payment = Payment.create(command.chargeId(), Amount.of(command.amount()));
            Payment savedPayment = paymentRepository.saveandFlush(payment);

            return new CreatePaymentResult(
                    savedPayment.getPaymentId(),
                    savedPayment.getChargeId(),
                    savedPayment.getPaymentStatus(),
                    savedPayment.getAmount(),
                    savedPayment.getCreatedAt()
            );

        }catch (DataIntegrityViolationException e){
            throw new PaymentException(PaymentErrorCode.DUPLICATE_CHARGE_ID);
        }
    }

    @Override
    @Transactional
    public SucceededPaymentResult succeededPayment(SucceededPaymentCommand command) {
        Payment payment = paymentRepository.findById(command.paymentId());

        payment.successPayment(command.paymentKey(), command.amount());

        return new SucceededPaymentResult(
                payment.getPaymentId(),
                payment.getPaymentStatus(),
                payment.getAmount(),
                payment.getUpdatedAt()
        );
    }

    @Override
    @Transactional
    public FailPaymentResult failPayment(FailPaymentCommand command) {
        Payment payment = paymentRepository.findById(command.paymentId());

        payment.failPayment(command.code(), command.message());

        return new FailPaymentResult(
                payment.getPaymentId(),
                payment.getPaymentStatus(),
                payment.getUpdatedAt()
        );
    }
}
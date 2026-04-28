package com.trustamarket.paymentservice.paymentservice.application.service;

import com.trustamarket.paymentservice.paymentservice.application.dto.command.CreatePaymentCommand;
import com.trustamarket.paymentservice.paymentservice.application.dto.result.CreatePaymentResult;
import com.trustamarket.paymentservice.paymentservice.application.port.PaymentUseCase;
import com.trustamarket.paymentservice.paymentservice.domain.entity.Payment;
import com.trustamarket.paymentservice.paymentservice.domain.repository.PaymentRepository;
import com.trustamarket.paymentservice.paymentservice.domain.vo.Amount;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PaymentService implements PaymentUseCase {

    private final PaymentRepository paymentRepository;

    @Override
    @Transactional
    public CreatePaymentResult createPayment(CreatePaymentCommand command) {
        Payment payment = Payment.create(Amount.of(command.amount()));

        Payment savedPayment = paymentRepository.save(payment);

        return new CreatePaymentResult(
                savedPayment.getPaymentId(),
                savedPayment.getPaymentStatus(),
                savedPayment.getAmount(),
                savedPayment.getCreatedAt()
        );
    }
}
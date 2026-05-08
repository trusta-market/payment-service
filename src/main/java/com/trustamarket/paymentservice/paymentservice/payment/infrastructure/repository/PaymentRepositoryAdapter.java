package com.trustamarket.paymentservice.paymentservice.payment.infrastructure.repository;

import com.trustamarket.paymentservice.paymentservice.payment.application.dto.query.PaymentSearchQuery;
import com.trustamarket.paymentservice.paymentservice.payment.domain.entity.Payment;
import com.trustamarket.paymentservice.paymentservice.payment.domain.exception.PaymentErrorCode;
import com.trustamarket.paymentservice.paymentservice.payment.domain.exception.PaymentException;
import com.trustamarket.paymentservice.paymentservice.payment.domain.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class PaymentRepositoryAdapter implements PaymentRepository {

    private final PaymentJpaRepository paymentJpaRepository;

    @Override
    public Payment saveAndFlush(Payment payment) {
        return paymentJpaRepository.saveAndFlush(payment);

    }

    @Override
    public Payment findById(UUID paymentId){
        Payment payment = paymentJpaRepository.findById(paymentId).
                orElseThrow(() -> new PaymentException(PaymentErrorCode.PAYMENT_NOT_FOUND));
        return payment;
    }

    @Override
    public Slice<Payment> searchPayments(PaymentSearchQuery query, Pageable pageable) {
        Specification<Payment> spec = PaymentSpecification.hasUserId(query.userId())
                .and(PaymentSpecification.hasPaymentId(query.paymentId()))
                .and(PaymentSpecification.hasStatusEq(query.status()))
                .and(PaymentSpecification.amountBetween(query.minAmount(), query.maxAmount()));

        return paymentJpaRepository.findAll(spec, pageable);
    }
}
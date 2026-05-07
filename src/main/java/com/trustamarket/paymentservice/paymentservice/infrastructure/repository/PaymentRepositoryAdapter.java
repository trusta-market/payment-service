package com.trustamarket.paymentservice.paymentservice.infrastructure.repository;

import com.trustamarket.paymentservice.paymentservice.application.dto.query.SearchPaymentQuery;
import com.trustamarket.paymentservice.paymentservice.domain.entity.Payment;
import com.trustamarket.paymentservice.paymentservice.domain.exception.PaymentErrorCode;
import com.trustamarket.paymentservice.paymentservice.domain.exception.PaymentException;
import com.trustamarket.paymentservice.paymentservice.domain.repository.PaymentRepository;
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
    public Slice<Payment> searchPayments(SearchPaymentQuery query, Pageable pageable) {
        Specification<Payment> spec = PaymentSpecification.hasUserId(query.paymentId())
                .and(PaymentSpecification.hasPaymentId(query.paymentId()))
                .and(PaymentSpecification.hasStatusEq(query.status()))
                .and(PaymentSpecification.amountBetween(query.minAmount(), query.maxAmount()));

        paymentJpaRepository.findAll(spec, pageable);

        return paymentJpaRepository.findAll(spec, pageable);
    }
}
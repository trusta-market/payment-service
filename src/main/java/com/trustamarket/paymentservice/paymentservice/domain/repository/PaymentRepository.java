package com.trustamarket.paymentservice.paymentservice.domain.repository;

import com.trustamarket.paymentservice.paymentservice.domain.repository.condition.PaymentSearchCondition;
import com.trustamarket.paymentservice.paymentservice.domain.entity.Payment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.UUID;

public interface PaymentRepository {
    Payment saveAndFlush(Payment payment);
    Payment findById(UUID paymentId);
    Slice<Payment> searchPayments(PaymentSearchCondition query, Pageable pageable);
}

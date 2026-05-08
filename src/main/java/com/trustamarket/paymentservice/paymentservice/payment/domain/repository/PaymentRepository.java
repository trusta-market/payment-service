package com.trustamarket.paymentservice.paymentservice.payment.domain.repository;

import com.trustamarket.paymentservice.paymentservice.payment.application.dto.query.PaymentSearchQuery;
import com.trustamarket.paymentservice.paymentservice.payment.domain.entity.Payment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.UUID;

public interface PaymentRepository {
    Payment saveAndFlush(Payment payment);
    Payment findById(UUID paymentId);
    Slice<Payment> searchPayments(PaymentSearchQuery query, Pageable pageable);
}

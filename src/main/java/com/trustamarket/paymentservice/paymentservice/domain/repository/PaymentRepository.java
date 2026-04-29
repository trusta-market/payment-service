package com.trustamarket.paymentservice.paymentservice.domain.repository;

import com.trustamarket.paymentservice.paymentservice.domain.entity.Payment;

import java.util.UUID;

public interface PaymentRepository {
    boolean existsByChargeId(UUID chargeId);
    Payment saveAndFlush(Payment payment);
    Payment findById(UUID PaymentId);
}

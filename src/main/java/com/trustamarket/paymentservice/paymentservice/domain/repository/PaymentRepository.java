package com.trustamarket.paymentservice.paymentservice.domain.repository;

import com.trustamarket.paymentservice.paymentservice.domain.entity.Payment;

public interface PaymentRepository {
    Payment save(Payment payment);
}

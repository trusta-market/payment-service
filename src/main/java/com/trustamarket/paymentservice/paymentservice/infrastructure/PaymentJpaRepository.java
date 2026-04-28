package com.trustamarket.paymentservice.paymentservice.infrastructure;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.trustamarket.paymentservice.paymentservice.domain.entity.Payment;

public interface PaymentJpaRepository extends JpaRepository<Payment, UUID> {
    boolean existsByChargeId(UUID chargeId);
}

package com.trustamarket.paymentservice.paymentservice.infrastructure.repository;

import com.trustamarket.paymentservice.paymentservice.domain.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PaymentJpaRepository extends JpaRepository<Payment, UUID> {
    boolean existsByChargeId(UUID chargeId);
}

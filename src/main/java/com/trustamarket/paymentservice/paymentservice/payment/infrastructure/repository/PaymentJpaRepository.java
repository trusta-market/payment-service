package com.trustamarket.paymentservice.paymentservice.payment.infrastructure.repository;

import com.trustamarket.paymentservice.paymentservice.payment.domain.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface PaymentJpaRepository extends JpaRepository<Payment, UUID>, JpaSpecificationExecutor<Payment> {
}

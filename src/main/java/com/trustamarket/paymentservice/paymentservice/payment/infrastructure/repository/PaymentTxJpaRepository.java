package com.trustamarket.paymentservice.paymentservice.payment.infrastructure.repository;

import com.trustamarket.paymentservice.paymentservice.payment.domain.entity.PaymentTx;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PaymentTxJpaRepository extends JpaRepository<PaymentTx, UUID> {
}

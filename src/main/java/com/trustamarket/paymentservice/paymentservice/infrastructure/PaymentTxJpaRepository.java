package com.trustamarket.paymentservice.paymentservice.infrastructure;

import com.trustamarket.paymentservice.paymentservice.domain.entity.PaymentTx;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PaymentTxJpaRepository extends JpaRepository<PaymentTx, UUID> {
}

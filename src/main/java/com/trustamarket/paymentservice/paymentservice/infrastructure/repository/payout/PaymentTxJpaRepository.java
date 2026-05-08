package com.trustamarket.paymentservice.paymentservice.infrastructure.repository.payout;

import com.trustamarket.paymentservice.paymentservice.domain.entity.PayoutTx;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PaymentTxJpaRepository extends JpaRepository<PayoutTx, UUID>{
}

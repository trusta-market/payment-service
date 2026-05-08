package com.trustamarket.paymentservice.paymentservice.payout.infrastructure.repository;

import com.trustamarket.paymentservice.paymentservice.payout.domain.entity.PayoutTx;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PayoutTxJpaRepository extends JpaRepository<PayoutTx, UUID> {
}

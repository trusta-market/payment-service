package com.trustamarket.paymentservice.paymentservice.infrastructure.repository.payout;

import com.trustamarket.paymentservice.paymentservice.domain.entity.Payout;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PayoutJpaRepository extends JpaRepository<Payout, UUID> {
}

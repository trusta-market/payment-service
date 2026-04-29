package com.trustamarket.paymentservice.paymentservice.infrastructure;

import com.trustamarket.paymentservice.paymentservice.domain.entity.Payout;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PayoutJpaRepository extends JpaRepository<Payout, Long> {
}

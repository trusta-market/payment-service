package com.trustamarket.paymentservice.paymentservice.payout.domain.repository;

import com.trustamarket.paymentservice.paymentservice.payout.domain.entity.Payout;

public interface PayoutRepository {
    Payout saveAndFlush(Payout payout);
}

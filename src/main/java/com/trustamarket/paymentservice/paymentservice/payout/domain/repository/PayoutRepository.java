package com.trustamarket.paymentservice.paymentservice.payout.domain.repository;

import com.trustamarket.paymentservice.paymentservice.payout.domain.entity.Payout;

import java.util.UUID;

public interface PayoutRepository {
    Payout saveAndFlush(Payout payout);
    boolean existsByPointTxHistory(UUID pointTxRequestHistoryId);
}

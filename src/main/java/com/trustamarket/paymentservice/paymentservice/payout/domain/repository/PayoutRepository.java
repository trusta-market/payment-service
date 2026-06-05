package com.trustamarket.paymentservice.paymentservice.payout.domain.repository;

import com.trustamarket.paymentservice.paymentservice.payout.domain.entity.Payout;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public interface PayoutRepository {
    Payout saveAndFlush(Payout payout);
    Payout findById(UUID payoutId);
    List<Payout> findProcessingPayouts(int limit);
    void resetToRequested(Instant threshold);
}

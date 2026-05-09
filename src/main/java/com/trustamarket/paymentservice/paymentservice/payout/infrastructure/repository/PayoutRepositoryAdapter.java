package com.trustamarket.paymentservice.paymentservice.payout.infrastructure.repository;

import com.trustamarket.paymentservice.paymentservice.payout.domain.entity.Payout;
import com.trustamarket.paymentservice.paymentservice.payout.domain.repository.PayoutRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class PayoutRepositoryAdapter implements PayoutRepository {
    private final PayoutJpaRepository payoutJpaRepository;

    @Override
    public Payout saveAndFlush(Payout payout) {
        return payoutJpaRepository.saveAndFlush(payout);
    }

    @Override
    public boolean existsByPointTxHistory(UUID pointTxRequestHistoryId) {
        return existsByPointTxHistory(pointTxRequestHistoryId);
    }
}

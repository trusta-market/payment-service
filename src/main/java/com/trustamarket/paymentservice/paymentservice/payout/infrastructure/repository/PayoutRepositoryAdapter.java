package com.trustamarket.paymentservice.paymentservice.payout.infrastructure.repository;

import com.trustamarket.paymentservice.paymentservice.payout.domain.entity.Payout;
import com.trustamarket.paymentservice.paymentservice.payout.domain.enums.PayoutStatus;
import com.trustamarket.paymentservice.paymentservice.payout.domain.repository.PayoutRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
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
    public Payout findById(UUID payoutId) {
        return payoutJpaRepository.findById(payoutId)
                .orElseThrow(() -> new IllegalArgumentException("출금요청 내용이 존재하지 않습니다."));
    }

    @Override
    @Transactional
    public void resetToRequested(Instant threshold) {
        payoutJpaRepository.resetToRequested(threshold);
    }

    @Override
    @Transactional
    public List<Payout> findProcessingPayouts(int limit) {
        List<UUID> ids = payoutJpaRepository.findByStatus(PayoutStatus.REQUESTED, PageRequest.of(0, limit, Sort.by("createdAt").ascending()))
                .stream().map(Payout::getPayoutId).toList();
        if (ids.isEmpty()) {
            return List.of();
        }
        payoutJpaRepository.updateStatusToProcessing(ids);
        return payoutJpaRepository.findByStatusAndPayoutIdIn(PayoutStatus.PROCESSING, ids);
    }
}

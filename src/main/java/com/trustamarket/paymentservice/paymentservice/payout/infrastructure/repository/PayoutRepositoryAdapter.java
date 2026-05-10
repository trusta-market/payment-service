package com.trustamarket.paymentservice.paymentservice.payout.infrastructure.repository;

import com.trustamarket.paymentservice.paymentservice.payout.domain.entity.Payout;
import com.trustamarket.paymentservice.paymentservice.payout.domain.exception.PayoutErrorCode;
import com.trustamarket.paymentservice.paymentservice.payout.domain.exception.PayoutException;
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
    public Payout findById(UUID payoutId) {
        Payout payout = payoutJpaRepository.findById(payoutId)
                .orElseThrow(()-> new IllegalArgumentException("출금요청 내용이 존재하지 않습니다."));
        return payout;
    }

    @Override
    public boolean existsByPointTxHistory(UUID pointTxRequestHistoryId) {
        return payoutJpaRepository.existsByPointTxHistory(pointTxRequestHistoryId);
    }
}

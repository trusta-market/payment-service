package com.trustamarket.paymentservice.paymentservice.payout.infrastructure.withdraw.retry;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class WalletWithdrawRetryProcessor {

    private final WalletWithdrawRetryJpaRepository retryRepository;

    @Transactional(readOnly = true)
    public List<WalletWithdrawRetry> findRetriable(LocalDateTime threshold) {
        return retryRepository.findRetriable(RetryStatus.PENDING, threshold);
    }

    @Transactional
    public void markSuccess(UUID id) {
        WalletWithdrawRetry retry = retryRepository.findById(id).orElseThrow();
        retry.markSuccess();
    }

    @Transactional
    public boolean recordFailure(UUID id) {
        WalletWithdrawRetry retry = retryRepository.findById(id).orElseThrow();
        retry.recordFailure();
        return retry.getStatus() == RetryStatus.FAILED;
    }
}

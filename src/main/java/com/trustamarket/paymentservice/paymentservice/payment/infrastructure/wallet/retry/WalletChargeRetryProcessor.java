package com.trustamarket.paymentservice.paymentservice.payment.infrastructure.wallet.retry;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class WalletChargeRetryProcessor {

    private final WalletChargeRetryJpaRepository retryRepository;

    @Transactional(readOnly = true)
    public List<WalletChargeRetry> findRetriable(LocalDateTime threshold) {
        return retryRepository.findRetriable(RetryStatus.PENDING, threshold);
    }

    @Transactional
    public void markSuccess(UUID id) {
        WalletChargeRetry retry = retryRepository.findById(id).orElseThrow();
        retry.markSuccess();
    }

    @Transactional
    public boolean recordFailure(UUID id) {
        WalletChargeRetry retry = retryRepository.findById(id).orElseThrow();
        retry.recordFailure();
        return retry.getStatus() == RetryStatus.FAILED;
    }
}

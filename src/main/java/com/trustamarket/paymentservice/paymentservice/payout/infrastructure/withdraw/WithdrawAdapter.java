package com.trustamarket.paymentservice.paymentservice.payout.infrastructure.withdraw;

import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;

import com.trustamarket.paymentservice.paymentservice.payout.application.port.out.wallet.PayoutCompletedResult;
import com.trustamarket.paymentservice.paymentservice.payout.application.port.out.wallet.WalletPort;
import com.trustamarket.paymentservice.paymentservice.payout.infrastructure.withdraw.dto.WithdrawCompletedRequest;
import com.trustamarket.paymentservice.paymentservice.payout.infrastructure.withdraw.retry.WalletWithdrawRetry;
import com.trustamarket.paymentservice.paymentservice.payout.infrastructure.withdraw.retry.WalletWithdrawRetryJpaRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class WithdrawAdapter implements WalletPort {

    private final WithdrawFeignClient walletFeignClient;
    private final WalletWithdrawRetryJpaRepository retryRepository;

    @Retryable(retryFor = Exception.class, maxAttempts = 3, backoff = @Backoff(delay = 1000, multiplier = 2))
    @Override
    public void payoutCompleted(PayoutCompletedResult result) {
        walletFeignClient.withdrawCompleted(new WithdrawCompletedRequest(
                result.userId(),
                result.payoutId(),
                result.pointTxRequestHistoryId(),
                result.payoutStatus(),
                result.payoutAmount()
        ));
    }

    @Recover
    public void recover(Exception e, PayoutCompletedResult result) {
        log.error("[WalletPayout] 3회 재시도 실패, retry 테이블 저장. payoutId={}", result.payoutId(), e);
        retryRepository.save(WalletWithdrawRetry.create(result));
    }
}

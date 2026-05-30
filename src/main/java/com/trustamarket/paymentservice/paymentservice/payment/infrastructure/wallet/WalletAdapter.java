package com.trustamarket.paymentservice.paymentservice.payment.infrastructure.wallet;

import com.trustamarket.paymentservice.paymentservice.payment.application.port.PaymentResponseResult;
import com.trustamarket.paymentservice.paymentservice.payment.application.port.WalletPort;
import com.trustamarket.paymentservice.paymentservice.payment.infrastructure.wallet.dto.PointWalletRequest;
import com.trustamarket.paymentservice.paymentservice.payment.infrastructure.wallet.retry.WalletChargeRetry;
import com.trustamarket.paymentservice.paymentservice.payment.infrastructure.wallet.retry.WalletChargeRetryJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class WalletAdapter implements WalletPort {

    private final WalletFeignClient walletFeignClient;
    private final WalletChargeRetryJpaRepository retryRepository;

    @Retryable(retryFor = Exception.class, maxAttempts = 3, backoff = @Backoff(delay = 1000, multiplier = 2))
    @Override
    public void pointToWallet(PaymentResponseResult result) {
        walletFeignClient.pointToWallet(new PointWalletRequest(
                result.userId(),
                result.paymentId(),
                result.pointTxRequestHistoryId(),
                result.paymentStatus(),
                result.amount()
        ));
    }

    @Recover
    public void recover(Exception e, PaymentResponseResult result) {
        log.error("[WalletCharge] 3회 재시도 실패, retry 테이블 저장. paymentId={}", result.paymentId(), e);
        retryRepository.save(WalletChargeRetry.create(result));
    }
}
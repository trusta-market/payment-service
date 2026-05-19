package com.trustamarket.paymentservice.paymentservice.payment.infrastructure.wallet;

import com.trustamarket.common.response.CommonResponse;
import com.trustamarket.paymentservice.paymentservice.payment.application.port.PaymentResponseResult;
import com.trustamarket.paymentservice.paymentservice.payment.application.port.WalletPort;
import com.trustamarket.paymentservice.paymentservice.payment.infrastructure.wallet.dto.PointWalletRequest;
import feign.FeignException;
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

    @Retryable(
            retryFor = FeignException.class,
            maxAttempts = 3,
            backoff = @Backoff(delay = 1000)
    )
    @Override
    public CommonResponse<Void> pointToWallet(PaymentResponseResult result){
        PointWalletRequest request = new PointWalletRequest(
                result.userId(),
                result.paymentId(),
                result.pointTxRequestHistoryId(),
                result.paymentStatus(),
                result.amount()
        );

        return walletFeignClient.pointToWallet(request);
    }

    @Recover
    public CommonResponse<Void> recover(FeignException e, PaymentResponseResult result) {
        log.error("[Wallet] 재시도 모두 실패. paymentId={}", result.paymentId(), e);
        return CommonResponse.of(500, null);
    }
}
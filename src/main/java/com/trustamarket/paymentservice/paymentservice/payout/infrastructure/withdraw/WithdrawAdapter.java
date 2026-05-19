package com.trustamarket.paymentservice.paymentservice.payout.infrastructure.withdraw;

import com.trustamarket.common.response.CommonResponse;
import com.trustamarket.paymentservice.paymentservice.payout.application.port.out.wallet.PayoutCompletedResult;
import com.trustamarket.paymentservice.paymentservice.payout.application.port.out.wallet.WalletPort;
import com.trustamarket.paymentservice.paymentservice.payout.infrastructure.withdraw.dto.WithdrawCompletedRequest;
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
public class WithdrawAdapter implements WalletPort {

    private final WithdrawFeignClient walletFeignClient;

    @Retryable(
            retryFor = FeignException.class,
            maxAttempts = 3,
            backoff = @Backoff(delay = 1000)
    )
    @Override
    public CommonResponse<Void> payoutCompleted(PayoutCompletedResult result) {
        WithdrawCompletedRequest request = new WithdrawCompletedRequest(
                result.userId(),
                result.payoutId(),
                result.pointTxRequestHistoryId(),
                result.payoutStatus(),
                result.payoutAmount()
        );

        return walletFeignClient.withdrawCompleted(request);
    }

    @Recover
    public CommonResponse<Void> recover(FeignException e, PayoutCompletedResult result) {
        log.error("[Wallet] 재시도 모두 실패. payoutId={}", result.payoutId(), e);
        return CommonResponse.of(500, null);
    }
}

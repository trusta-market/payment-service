package com.trustamarket.paymentservice.paymentservice.payout.infrastructure.withdraw.retry;

import com.trustamarket.paymentservice.paymentservice.payout.infrastructure.notification.PayoutNotificationClient;
import com.trustamarket.paymentservice.paymentservice.payout.infrastructure.withdraw.WithdrawFeignClient;
import com.trustamarket.paymentservice.paymentservice.payout.infrastructure.withdraw.dto.WithdrawCompletedRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class WalletWithdrawRetryScheduler {

    private static final int MAX_RETRY = 2;

    private final WalletWithdrawRetryProcessor processor;
    private final WithdrawFeignClient withdrawFeignClient;
    private final PayoutNotificationClient notificationClient;

    @Scheduled(fixedDelay = 300_000)
    public void process() {
        LocalDateTime threshold = LocalDateTime.now().minusMinutes(5);
        List<WalletWithdrawRetry> targets = processor.findRetriable(threshold);

        for (WalletWithdrawRetry retry : targets) {
            try {
                withdrawFeignClient.withdrawCompleted(new WithdrawCompletedRequest(
                        retry.getUserId(),
                        retry.getPayoutId(),
                        retry.getPointTxRequestHistoryId(),
                        retry.getPayoutStatus(),
                        retry.getAmount()
                ));
                processor.markSuccess(retry.getId());
                log.info("[WithdrawRetry] 전달 성공. payoutId={}", retry.getPayoutId());

            } catch (Exception e) {
                boolean isFailed = processor.recordFailure(retry.getId());
                log.error("[WithdrawRetry] 재시도 실패. payoutId={}, retryCount={}", retry.getPayoutId(), retry.getRetryCount(), e);

                if (isFailed) {
                    notificationClient.send(String.format(
                            "[출금 알림 실패] wallet 전달 %d회 모두 실패\npayoutId: %s\n수동 처리가 필요합니다.",
                            MAX_RETRY, retry.getPayoutId()
                    ));
                }
            }
        }
    }
}

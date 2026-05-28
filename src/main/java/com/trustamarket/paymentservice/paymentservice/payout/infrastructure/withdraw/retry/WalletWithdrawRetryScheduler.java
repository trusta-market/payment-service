package com.trustamarket.paymentservice.paymentservice.payout.infrastructure.withdraw.retry;

import com.trustamarket.paymentservice.paymentservice.payout.infrastructure.notification.PayoutSlackNotificationClient;
import com.trustamarket.paymentservice.paymentservice.payout.infrastructure.withdraw.WithdrawFeignClient;
import com.trustamarket.paymentservice.paymentservice.payout.infrastructure.withdraw.dto.WithdrawCompletedRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class WalletWithdrawRetryScheduler {

    private static final int ALERT_THRESHOLD = 3;
    private static final int MAX_RETRY = 5;

    private final WalletWithdrawRetryJpaRepository retryRepository;
    private final WithdrawFeignClient withdrawFeignClient;
    private final PayoutSlackNotificationClient slackClient;

    @Scheduled(fixedDelay = 300_000)
    @Transactional
    public void process() {
        LocalDateTime threshold = LocalDateTime.now().minusMinutes(5);
        List<WalletWithdrawRetry> targets = retryRepository.findRetriable(RetryStatus.PENDING, threshold);

        for (WalletWithdrawRetry retry : targets) {
            try {
                withdrawFeignClient.withdrawCompleted(new WithdrawCompletedRequest(
                        retry.getUserId(),
                        retry.getPayoutId(),
                        retry.getPointTxRequestHistoryId(),
                        retry.getPayoutStatus(),
                        retry.getAmount()
                ));
                retry.markSuccess();
                log.info("[PayoutRetry] 전달 성공. payoutId={}", retry.getPayoutId());

            } catch (Exception e) {
                retry.recordFailure();
                log.error("[PayoutRetry] 재시도 실패. payoutId={}, retryCount={}", retry.getPayoutId(), retry.getRetryCount(), e);

                if (retry.needsAdminAlert()) {
                    slackClient.send(String.format(
                            "[출금 알림 재시도 경고] wallet 전달 %d회 실패\npayoutId: %s\n확인이 필요합니다.",
                            ALERT_THRESHOLD, retry.getPayoutId()
                    ));
                }
                if (retry.getStatus() == RetryStatus.FAILED) {
                    slackClient.send(String.format(
                            "[출금 알림 재시도 실패] wallet 전달 %d회 모두 실패\npayoutId: %s\n수동 처리가 필요합니다.",
                            MAX_RETRY, retry.getPayoutId()
                    ));
                }
            }
        }
    }
}

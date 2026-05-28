package com.trustamarket.paymentservice.paymentservice.payment.infrastructure.wallet.retry;

import com.trustamarket.paymentservice.paymentservice.payment.infrastructure.notification.PaymentSlackNotificationClient;
import com.trustamarket.paymentservice.paymentservice.payment.infrastructure.wallet.WalletFeignClient;
import com.trustamarket.paymentservice.paymentservice.payment.infrastructure.wallet.dto.PointWalletRequest;
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
public class WalletChargeRetryScheduler {

    private static final int ALERT_THRESHOLD = 3;
    private static final int MAX_RETRY = 5;

    private final WalletChargeRetryJpaRepository retryRepository;
    private final WalletFeignClient walletFeignClient;
    private final PaymentSlackNotificationClient slackClient;

    @Scheduled(fixedDelay = 300_000)
    @Transactional
    public void process() {
        LocalDateTime threshold = LocalDateTime.now().minusMinutes(5);
        List<WalletChargeRetry> targets = retryRepository.findRetriable(RetryStatus.PENDING, threshold);

        for (WalletChargeRetry retry : targets) {
            try {
                walletFeignClient.pointToWallet(new PointWalletRequest(
                        retry.getUserId(),
                        retry.getPaymentId(),
                        retry.getPointTxRequestHistoryId(),
                        retry.getPaymentStatus(),
                        retry.getAmount()
                ));
                retry.markSuccess();
                log.info("[ChargeRetry] 전달 성공. paymentId={}", retry.getPaymentId());

            } catch (Exception e) {
                retry.recordFailure();
                log.error("[ChargeRetry] 재시도 실패. paymentId={}, retryCount={}", retry.getPaymentId(), retry.getRetryCount(), e);

                if (retry.needsAdminAlert()) {
                    slackClient.send(String.format(
                            "[결제 알림 재시도 경고] wallet 전달 %d회 실패\npaymentId: %s\n확인이 필요합니다.",
                            ALERT_THRESHOLD, retry.getPaymentId()
                    ));
                }
                if (retry.getStatus() == RetryStatus.FAILED) {
                    slackClient.send(String.format(
                            "[결제 알림 재시도 실패] wallet 전달 %d회 모두 실패\npaymentId: %s\n수동 처리가 필요합니다.",
                            MAX_RETRY, retry.getPaymentId()
                    ));
                }
            }
        }
    }
}

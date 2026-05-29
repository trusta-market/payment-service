package com.trustamarket.paymentservice.paymentservice.payment.infrastructure.wallet.retry;

import com.trustamarket.paymentservice.paymentservice.payment.infrastructure.notification.PaymentNotification;
import com.trustamarket.paymentservice.paymentservice.payment.infrastructure.wallet.WalletFeignClient;
import com.trustamarket.paymentservice.paymentservice.payment.infrastructure.wallet.dto.PointWalletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class WalletChargeRetryScheduler {

    private static final int MAX_RETRY = 2;

    private final WalletChargeRetryProcessor processor;
    private final WalletFeignClient walletFeignClient;
    private final PaymentNotification notificationClient;

    @Scheduled(fixedDelay = 300_000)
    public void process() {
        LocalDateTime threshold = LocalDateTime.now().minusMinutes(5);
        List<WalletChargeRetry> targets = processor.findRetriable(threshold);

        for (WalletChargeRetry retry : targets) {
            try {
                walletFeignClient.pointToWallet(new PointWalletRequest(
                        retry.getUserId(),
                        retry.getPaymentId(),
                        retry.getPointTxRequestHistoryId(),
                        retry.getPaymentStatus(),
                        retry.getAmount()
                ));
                processor.markSuccess(retry.getId());
                log.info("[ChargeRetry] 전달 성공. paymentId={}", retry.getPaymentId());

            } catch (Exception e) {
                boolean isFailed = processor.recordFailure(retry.getId());
                log.error("[ChargeRetry] 재시도 실패. paymentId={}, retryCount={}", retry.getPaymentId(), retry.getRetryCount(), e);

                if (isFailed) {
                    notificationClient.send(String.format(
                            "[결제 알림 실패] wallet 전달 %d회 모두 실패\npaymentId: %s\n수동 처리가 필요합니다.",
                            MAX_RETRY, retry.getPaymentId()
                    ));
                }
            }
        }
    }
}

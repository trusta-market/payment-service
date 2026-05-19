package com.trustamarket.paymentservice.paymentservice.payout.infrastructure.pgMock;

import com.trustamarket.paymentservice.paymentservice.payout.application.port.out.pgClient.PgClientPort;
import com.trustamarket.paymentservice.paymentservice.payout.application.port.out.pgClient.PgPayoutRequest;
import com.trustamarket.paymentservice.paymentservice.payout.application.port.out.pgClient.PgPayoutResult;
import com.trustamarket.paymentservice.paymentservice.payout.domain.enums.PayoutStatus;
import com.trustamarket.paymentservice.paymentservice.payout.domain.exception.PayoutErrorCode;
import com.trustamarket.paymentservice.paymentservice.payout.domain.exception.PayoutException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;

import java.util.Random;

@Slf4j
@Component
@Profile("mocktest")
public class PgMockClient implements PgClientPort {

    @Value("${toss.mock.failure-rate}")
    private double failureRate;

    private final Random random = new Random();

    @Retryable(
            retryFor = PayoutException.class,
            maxAttempts = 3,
            backoff = @Backoff(delay = 2000)
    )
    @Override
    public PgPayoutResult requestPayout(PgPayoutRequest request) {

        // 실제 PG사 연결시 sleep 제거, PG사 클라이언트 대체 필수
        // 처리 시간 시뮬레이션 3초
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        if (random.nextDouble() < failureRate) { //PG사 실패 확률
            throw new PayoutException(PayoutErrorCode.PAYOUT_CONFIRM_UNKNOWN);
        }

        return new PgPayoutResult(PayoutStatus.SUCCESS, null);

    }

    @Recover
    public PgPayoutResult recover(PayoutException e, PgPayoutRequest request) {
        log.error("[PG] 재시도 모두 실패. payoutId={}", request.payoutId(), e);
        return new PgPayoutResult(PayoutStatus.FAILED, e.getMessage());
    }
}

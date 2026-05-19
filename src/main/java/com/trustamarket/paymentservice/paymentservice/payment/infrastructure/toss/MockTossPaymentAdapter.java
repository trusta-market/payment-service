package com.trustamarket.paymentservice.paymentservice.payment.infrastructure.toss;

import com.trustamarket.paymentservice.paymentservice.payment.application.dto.result.TossConfirmResult;
import com.trustamarket.paymentservice.paymentservice.payment.application.port.TossPaymentPort;
import com.trustamarket.paymentservice.paymentservice.payment.domain.exception.PaymentErrorCode;
import com.trustamarket.paymentservice.paymentservice.payment.domain.exception.PaymentException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.UUID;

@Slf4j
@Component
@Profile("mocktest")
public class MockTossPaymentAdapter implements TossPaymentPort {

    @Value("${toss.mock.slow-rate}")
    private double slowRate;

    @Value("${toss.mock.failure-rate}")
    private double failureRate;

    private final Random random = new Random();

    @Retryable(
            retryFor = IllegalStateException.class,
            maxAttempts = 3,
            backoff = @Backoff(delay = 2000)
    )
    @Override
    public TossConfirmResult confirm(String paymentKey, UUID paymentId, long amount) {

        if (amount <= 0) {
            throw new PaymentException(PaymentErrorCode.INVALID_PAYMENT_AMOUNT);
        }

        simulateDelay();

        if (random.nextDouble() < failureRate) { //PG사 실패 확률
            throw new IllegalStateException("PG 처리 중 알 수 없는 오류가 발생했습니다.");
        }

        return new TossConfirmResult(paymentKey, paymentId.toString(), amount);
    }

    private void simulateDelay() {
        int delay;

        //일반적으로 1초 이내 처리, 늦을 경우 ~3초까지 걸림
        if (random.nextDouble() < slowRate) {
            // 5% - 느린 케이스: 1,000 ~ 3,000ms
            delay = 1000 + random.nextInt(2000);
        } else {
            // 95% - 정상 케이스: 100 ~ 1,000ms
            delay = 100 + random.nextInt(900);
        }

        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new PaymentException(PaymentErrorCode.PAYMENT_CONFIRM_UNKNOWN);
        }
    }

    @Recover
    public TossConfirmResult recover(IllegalStateException e, String paymentKey, UUID paymentId, long amount) {
        log.error("[Toss] 재시도 모두 실패. paymentId={}", paymentId, e);
        throw new PaymentException(PaymentErrorCode.PAYMENT_CONFIRM_UNKNOWN);
    }
}
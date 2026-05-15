package com.trustamarket.paymentservice.paymentservice.payment.infrastructure.toss;

import com.trustamarket.paymentservice.paymentservice.payment.application.dto.result.TossConfirmResult;
import com.trustamarket.paymentservice.paymentservice.payment.application.port.TossPaymentPort;
import com.trustamarket.paymentservice.paymentservice.payment.domain.exception.PaymentErrorCode;
import com.trustamarket.paymentservice.paymentservice.payment.domain.exception.PaymentException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.UUID;

@Component
@Profile("mocktest")
public class MockTossPaymentAdapter implements TossPaymentPort {

    @Value("${toss.mock.slow-rate}")
    private double slowRate;

    @Value("${toss.mock.failure-rate}")
    private double failureRate;

    private final Random random = new Random();

    @Override
    public TossConfirmResult confirm(String paymentKey, UUID paymentId, long amount) {

        if (amount <= 0) {
            throw new PaymentException(PaymentErrorCode.INVALID_PAYMENT_AMOUNT);
        }

        simulateDelay();

        if (random.nextDouble() < failureRate) { //PG사 실패 확률
            throw new PaymentException(PaymentErrorCode.PAYMENT_CONFIRM_UNKNOWN);
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
}
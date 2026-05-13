package com.trustamarket.paymentservice.paymentservice.payment.infrastructure.toss;

import com.trustamarket.paymentservice.paymentservice.payment.application.dto.result.TossConfirmResult;
import com.trustamarket.paymentservice.paymentservice.payment.application.port.TossPaymentPort;
import com.trustamarket.paymentservice.paymentservice.payment.domain.exception.PaymentErrorCode;
import com.trustamarket.paymentservice.paymentservice.payment.domain.exception.PaymentException;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Profile("mocktest")
public class MockTossPaymentAdapter implements TossPaymentPort {

    @Override
    public TossConfirmResult confirm(String paymentKey, UUID paymentId, long amount) {

        if(amount <= 0 ){
            throw new PaymentException(PaymentErrorCode.INVALID_PAYMENT_AMOUNT);
        }

        //처리 시간 시뮬레이션 2초
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        return new TossConfirmResult(
                paymentKey,
                paymentId.toString(),
                amount
        );
    }
}
package com.trustamarket.paymentservice.paymentservice.payment.infrastructure.toss;

import com.trustamarket.paymentservice.paymentservice.payment.application.dto.result.TossConfirmResult;
import com.trustamarket.paymentservice.paymentservice.payment.application.port.TossPaymentPort;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Profile("mocktest")
public class MockTossPaymentAdapter implements TossPaymentPort {

    @Override
    public TossConfirmResult confirm(String paymentKey, UUID paymentId, long amount) {
        return new TossConfirmResult(
                paymentKey,
                paymentId.toString(),
                amount
        );
    }
}
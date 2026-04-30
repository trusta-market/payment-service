package com.trustamarket.paymentservice.paymentservice.infrastructure.toss;

import com.trustamarket.paymentservice.paymentservice.application.dto.result.TossConfirmResult;
import com.trustamarket.paymentservice.paymentservice.application.port.TossPaymentPort;
import com.trustamarket.paymentservice.paymentservice.infrastructure.toss.dto.TossConfirmRequest;
import com.trustamarket.paymentservice.paymentservice.infrastructure.toss.dto.TossConfirmResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class TossPaymentAdapter implements TossPaymentPort {

    private final TossPaymentFeignClient tossPaymentFeignClient;

    @Override
    public TossConfirmResult confirm(String paymentKey, UUID chargeId, long amount) {
        String orderId = chargeId.toString();

        TossConfirmResponse response = tossPaymentFeignClient.confirm(
                new TossConfirmRequest(paymentKey, orderId, amount)
        );

        return new TossConfirmResult(
                response.paymentKey(),
                response.orderId(),
                response.status(),
                response.method(),
                response.totalAmount()
        );
    }
}

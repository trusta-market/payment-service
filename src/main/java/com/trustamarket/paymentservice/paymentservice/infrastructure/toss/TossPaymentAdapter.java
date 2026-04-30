package com.trustamarket.paymentservice.paymentservice.infrastructure.toss;

import com.trustamarket.paymentservice.paymentservice.application.dto.result.TossConfirmResult;
import com.trustamarket.paymentservice.paymentservice.application.port.TossPaymentPort;
import com.trustamarket.paymentservice.paymentservice.domain.exception.PaymentErrorCode;
import com.trustamarket.paymentservice.paymentservice.domain.exception.PaymentException;
import com.trustamarket.paymentservice.paymentservice.infrastructure.toss.dto.TossConfirmRequest;
import com.trustamarket.paymentservice.paymentservice.infrastructure.toss.dto.TossConfirmResponse;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class TossPaymentAdapter implements TossPaymentPort {

    private final TossPaymentFeignClient tossPaymentFeignClient;

    @Override
    public TossConfirmResult confirm(String paymentKey, UUID paymentId, long amount) {
        String orderId = paymentId.toString();

        try{
            TossConfirmResponse response = tossPaymentFeignClient.confirm(
                    new TossConfirmRequest(paymentKey, orderId, amount)
            );

            return new TossConfirmResult(
                    response.paymentId(),
                    response.paymentKey(),
                    response.status(),
                    response.method(),
                    response.totalAmount()
            );
        } catch (FeignException e) {
            throw new PaymentException(PaymentErrorCode.PAYMENT_CONFIRM_UNKNOWN);
        }
    }
}

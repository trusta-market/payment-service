package com.trustamarket.paymentservice.paymentservice.payment.infrastructure.toss;

import com.trustamarket.paymentservice.paymentservice.payment.application.dto.result.TossConfirmResult;
import com.trustamarket.paymentservice.paymentservice.payment.application.port.TossPaymentPort;
import com.trustamarket.paymentservice.paymentservice.payment.domain.exception.PaymentErrorCode;
import com.trustamarket.paymentservice.paymentservice.payment.domain.exception.PaymentException;
import com.trustamarket.paymentservice.paymentservice.payment.infrastructure.toss.dto.TossConfirmRequest;
import com.trustamarket.paymentservice.paymentservice.payment.infrastructure.toss.dto.TossConfirmResponse;
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

            if(!response.status().equals("DONE")){
                throw new PaymentException(PaymentErrorCode.PAYMENT_CONFIRM_UNKNOWN);
            }

            return new TossConfirmResult(
                    response.paymentKey(),
                    response.orderId(),
                    response.totalAmount()
            );

            //todo : 값 불일치시 취소 API 구현필요
            //todo : RetryableException 추가
        } catch (FeignException e) {
            throw new PaymentException(PaymentErrorCode.PAYMENT_CONFIRM_UNKNOWN);
        }
    }
}

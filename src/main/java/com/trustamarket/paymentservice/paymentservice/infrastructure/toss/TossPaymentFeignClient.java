package com.trustamarket.paymentservice.paymentservice.infrastructure.toss;

import com.trustamarket.paymentservice.paymentservice.infrastructure.toss.dto.TossConfirmRequest;
import com.trustamarket.paymentservice.paymentservice.infrastructure.toss.dto.TossConfirmResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        contextId = "tossPaymentConfirmClient",
        name = "tossPaymentClient",
        url = "${toss.payment.base-url}",
        configuration = TossFeignConfig.class
)
public interface TossPaymentFeignClient {

    @PostMapping("/v1/payments/confirm")
    TossConfirmResponse confirm(@RequestBody TossConfirmRequest request);
}

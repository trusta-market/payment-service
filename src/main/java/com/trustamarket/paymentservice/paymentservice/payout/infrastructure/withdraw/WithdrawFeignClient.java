package com.trustamarket.paymentservice.paymentservice.payout.infrastructure.withdraw;

import com.trustamarket.common.response.CommonResponse;
import com.trustamarket.paymentservice.paymentservice.payout.infrastructure.withdraw.dto.WithdrawCompletedRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name="wallet-service", contextId = "wallet-payout")
public interface WithdrawFeignClient {

    @PostMapping("/internal/v1/wallets/withdrawals")
    ResponseEntity<CommonResponse<Void>> withdrawCompleted(@RequestBody WithdrawCompletedRequest request);
}

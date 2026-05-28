package com.trustamarket.paymentservice.paymentservice.payment.infrastructure.wallet;

import com.trustamarket.common.response.CommonResponse;
import com.trustamarket.paymentservice.paymentservice.payment.infrastructure.wallet.dto.PointWalletRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name="wallet-service", contextId = "wallet-payment")
public interface WalletFeignClient {

    @PostMapping("/internal/v1/wallets/charges")
    ResponseEntity<Void> pointToWallet(@RequestBody PointWalletRequest request);
}
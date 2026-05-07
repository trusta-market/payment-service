package com.trustamarket.paymentservice.paymentservice.infrastructure.wallet;

import com.trustamarket.common.response.CommonResponse;
import com.trustamarket.paymentservice.paymentservice.infrastructure.wallet.dto.PointWalletRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name="wallet-service")
public interface WalletFeignClient {

    @PostMapping("/internal/v1/wallets/charges")
    CommonResponse<Void> pointToWallet(@RequestBody PointWalletRequest request);
}
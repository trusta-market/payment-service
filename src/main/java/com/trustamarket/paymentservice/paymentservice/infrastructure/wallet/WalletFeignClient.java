package com.trustamarket.paymentservice.paymentservice.infrastructure.wallet;

import com.trustamarket.paymentservice.paymentservice.infrastructure.wallet.dto.PointWalletRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.UUID;

@FeignClient(name="wallet-service")
public interface WalletFeignClient {

    @PostMapping("/internal/wallets/{userId}/charge")
    void pointToWallet(
            @PathVariable UUID userId,
            @RequestBody PointWalletRequest request
    );
}

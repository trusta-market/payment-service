package com.trustamarket.paymentservice.paymentservice.infrastructure.wallet;

import com.trustamarket.paymentservice.paymentservice.application.port.WalletPort;
import com.trustamarket.paymentservice.paymentservice.infrastructure.wallet.dto.PointWalletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class WalletAdapter implements WalletPort {

    private final WalletFeignClient walletFeignClient;

    @Override
    public void pointToWallet(UUID paymentId, long chargedAmount){
        PointWalletRequest request = new PointWalletRequest(
                paymentId, chargedAmount
        );

        UUID userId = UUID.randomUUID(); // 임시 userId전달
        walletFeignClient.pointToWallet(userId, request);
    }
}

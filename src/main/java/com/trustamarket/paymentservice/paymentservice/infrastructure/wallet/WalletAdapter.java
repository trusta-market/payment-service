package com.trustamarket.paymentservice.paymentservice.infrastructure.wallet;

import com.trustamarket.common.response.CommonResponse;
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
    public CommonResponse<Void> pointToWallet(UUID userId, UUID paymentId, long chargedAmount){
        PointWalletRequest request = new PointWalletRequest(
                userId, paymentId, chargedAmount
        );

        return walletFeignClient.pointToWallet(request);
    }
}
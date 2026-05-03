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
    public CommonResponse<Void> pointToWallet(UUID paymentId, long chargedAmount){
        PointWalletRequest request = new PointWalletRequest(
                paymentId, chargedAmount
        );

        //// 임시 userId전달 추후에 헤더값의 uerId로 변경
        UUID userId = UUID.randomUUID();
        return walletFeignClient.pointToWallet(userId, request);
    }
}

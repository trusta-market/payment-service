package com.trustamarket.paymentservice.paymentservice.payment.infrastructure.wallet;

import com.trustamarket.common.response.CommonResponse;
import com.trustamarket.paymentservice.paymentservice.payment.application.port.PaymentResponseResult;
import com.trustamarket.paymentservice.paymentservice.payment.application.port.WalletPort;
import com.trustamarket.paymentservice.paymentservice.payment.infrastructure.wallet.dto.PointWalletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WalletAdapter implements WalletPort {

    private final WalletFeignClient walletFeignClient;

    @Override
    public CommonResponse<Void> pointToWallet(PaymentResponseResult result){
        PointWalletRequest request = new PointWalletRequest(
                result.userId(),
                result.paymentId(),
                result.pointTxRequestHistoryId(),
                result.paymentStatus(),
                result.amount()
        );

        return walletFeignClient.pointToWallet(request);
    }
}
package com.trustamarket.paymentservice.paymentservice.payment.infrastructure.wallet;

import com.trustamarket.common.response.CommonResponse;
import com.trustamarket.paymentservice.paymentservice.payment.application.port.PaymentResponseResult;
import com.trustamarket.paymentservice.paymentservice.payment.application.port.WalletPort;
import com.trustamarket.paymentservice.paymentservice.payment.infrastructure.wallet.dto.PointWalletRequest;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class WalletAdapter implements WalletPort {

    private final WalletFeignClient walletFeignClient;

    @Override
    public void pointToWallet(PaymentResponseResult result){
        PointWalletRequest request = new PointWalletRequest(
                result.userId(),
                result.paymentId(),
                result.pointTxRequestHistoryId(),
                result.paymentStatus(),
                result.amount()
        );

        walletFeignClient.pointToWallet(request);
    }
}
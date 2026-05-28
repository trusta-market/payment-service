package com.trustamarket.paymentservice.paymentservice.payout.infrastructure.withdraw;

import org.springframework.stereotype.Component;

import com.trustamarket.paymentservice.paymentservice.payout.application.port.out.wallet.PayoutCompletedResult;
import com.trustamarket.paymentservice.paymentservice.payout.application.port.out.wallet.WalletPort;
import com.trustamarket.paymentservice.paymentservice.payout.infrastructure.withdraw.dto.WithdrawCompletedRequest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class WithdrawAdapter implements WalletPort {

    private final WithdrawFeignClient walletFeignClient;

    @Override
    public void payoutCompleted(PayoutCompletedResult result) {
        WithdrawCompletedRequest request = new WithdrawCompletedRequest(
                result.userId(),
                result.payoutId(),
                result.pointTxRequestHistoryId(),
                result.payoutStatus(),
                result.payoutAmount()
        );

        walletFeignClient.withdrawCompleted(request);
    }
}

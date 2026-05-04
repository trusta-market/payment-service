package com.trustamarket.paymentservice.paymentservice.infrastructure.wallet.dto;

import java.util.UUID;

public record PointWalletRequest (
        UUID userId,
        UUID paymentId,
        long chargeAmount
){
    public PointWalletRequest {
        if(paymentId == null) {
            throw new IllegalArgumentException("paymentId는 필수값입니다.");
        }
        if(chargeAmount <= 0) {
            //todo : long -> Amount
            throw new IllegalArgumentException("결제 금액은 0보다 커야 합니다.");
        }
    }
}
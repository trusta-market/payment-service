package com.trustamarket.paymentservice.paymentservice.infrastructure.wallet.dto;

import com.trustamarket.paymentservice.paymentservice.domain.exception.PaymentErrorCode;
import com.trustamarket.paymentservice.paymentservice.domain.exception.PaymentException;

import java.util.UUID;

public record PointWalletRequest (
    UUID paymentId,
    long chargedAmount
){
    public PointWalletRequest {
        if(paymentId == null) {
            throw new PaymentException(PaymentErrorCode.INVALID_PAYMENT_KEY);
        }
        if(chargedAmount <= 0) {
            //todo : long -> Amount
            throw new IllegalArgumentException("결제 금액은 0보다 커야 합니다.");
        }
    }
}

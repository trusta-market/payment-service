package com.trustamarket.paymentservice.paymentservice.infrastructure.wallet.dto;

import com.trustamarket.paymentservice.paymentservice.domain.entity.Payment;
import com.trustamarket.paymentservice.paymentservice.domain.exception.PaymentErrorCode;
import com.trustamarket.paymentservice.paymentservice.domain.exception.PaymentException;

import java.util.UUID;

public record PointWalletRequest (
        UUID userId,
        UUID paymentId,
        long chargeAmount
){
    public PointWalletRequest {
        if(userId == null) {
            throw new PaymentException(PaymentErrorCode.USER_ID_REQUIRED);
        }
        if(paymentId == null) {
            throw new PaymentException(PaymentErrorCode.PAYMENT_ID_REQUIRED);
        }
        if(chargeAmount <= 0) {
            //todo : long -> Amount
            throw new PaymentException(PaymentErrorCode.INVALID_PAYMENT_AMOUNT);
        }
    }
}
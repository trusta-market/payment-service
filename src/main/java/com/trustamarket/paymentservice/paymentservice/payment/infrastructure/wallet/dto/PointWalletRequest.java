package com.trustamarket.paymentservice.paymentservice.payment.infrastructure.wallet.dto;

import com.trustamarket.paymentservice.paymentservice.payment.domain.enums.PaymentStatus;
import com.trustamarket.paymentservice.paymentservice.payment.domain.exception.PaymentErrorCode;
import com.trustamarket.paymentservice.paymentservice.payment.domain.exception.PaymentException;

import java.util.UUID;

public record PointWalletRequest (
        UUID userId,
        UUID paymentId,
        UUID pointTxRequestHistoryId,
        PaymentStatus paymentStatus,
        long chargeAmount
) {
    public PointWalletRequest {
        if (userId == null) {
            throw new PaymentException(PaymentErrorCode.USER_ID_REQUIRED);
        }
        if (pointTxRequestHistoryId == null) {
            throw new PaymentException(PaymentErrorCode.REQUEST_ID_REQUIRED);
        }
        if (chargeAmount <= 0) {
            //todo : long -> Amount
            throw new PaymentException(PaymentErrorCode.INVALID_PAYMENT_AMOUNT);
        }
    }
}
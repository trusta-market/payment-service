package com.trustamarket.paymentservice.paymentservice.payment.application.port;

import com.trustamarket.paymentservice.paymentservice.payment.domain.entity.Payment;
import com.trustamarket.paymentservice.paymentservice.payment.domain.enums.PaymentStatus;

import java.util.UUID;

public record PaymentResponseResult (
        UUID userId,
        UUID paymentId,
        UUID pointTxRequestHistoryId,
        PaymentStatus paymentStatus,
        long amount
) {
    public static PaymentResponseResult from(Payment payment) {
        return new PaymentResponseResult(
                payment.getUserId(),
                payment.getPaymentId(),
                payment.getPointTxRequestHistoryId(),
                payment.getPaymentStatus(),
                payment.getAmount()
        );
    }
}


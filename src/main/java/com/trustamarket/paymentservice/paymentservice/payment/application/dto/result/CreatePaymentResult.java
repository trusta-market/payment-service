package com.trustamarket.paymentservice.paymentservice.payment.application.dto.result;

import com.trustamarket.paymentservice.paymentservice.payment.domain.entity.Payment;

import java.time.Instant;
import java.util.UUID;

public record CreatePaymentResult (
        UUID paymentId,
        UUID pointTxRequestHistoryId,
        long chargeAmount,
        Instant createdAt
){
    public static CreatePaymentResult from(Payment payment) {
        return new CreatePaymentResult(
                payment.getPaymentId(),
                payment.getPointTxRequestHistoryId(),
                payment.getAmount(),
                payment.getCreatedAt()
        );
    }
}

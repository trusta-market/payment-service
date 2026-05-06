package com.trustamarket.paymentservice.paymentservice.application.dto.command;

import com.trustamarket.paymentservice.paymentservice.domain.exception.PaymentErrorCode;
import com.trustamarket.paymentservice.paymentservice.domain.exception.PaymentException;

import java.util.UUID;

public record CreatePaymentCommand (
        UUID userId,
        UUID paymentId,
        long amount
){
    public CreatePaymentCommand {
        if(userId == null) {
            throw new PaymentException(PaymentErrorCode.USER_ID_REQUIRED);
        }
        if(paymentId == null) {
            throw new PaymentException(PaymentErrorCode.PAYMENT_ID_REQUIRED);
        }
        if(amount <= 0) {
            throw new PaymentException(PaymentErrorCode.INVALID_PAYMENT_AMOUNT);
        }
    }
}

package com.trustamarket.paymentservice.paymentservice.payment.application.dto.command;

import com.trustamarket.paymentservice.paymentservice.payment.domain.exception.PaymentErrorCode;
import com.trustamarket.paymentservice.paymentservice.payment.domain.exception.PaymentException;
import com.trustamarket.paymentservice.paymentservice.payment.domain.vo.Amount;

import java.util.UUID;

public record CreatePaymentCommand (
        UUID userId,
        UUID pointTxRequestHistoryId,
        Amount amount
){
    public CreatePaymentCommand {
        if(userId == null) {
            throw new PaymentException(PaymentErrorCode.USER_ID_REQUIRED);
        }
        if(pointTxRequestHistoryId == null) {
            throw new PaymentException(PaymentErrorCode.REQUEST_ID_REQUIRED);
        }
    }
}

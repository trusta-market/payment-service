package com.trustamarket.paymentservice.paymentservice.payout.domain.exception;

import com.trustamarket.common.exception.CustomException;
import com.trustamarket.common.exception.ErrorCodeSpec;

public class PayoutException extends CustomException {
    private final ErrorCodeSpec errorCode;

    public PayoutException(ErrorCodeSpec errorCode) {
        super(errorCode);
        this.errorCode = errorCode;
    }
}

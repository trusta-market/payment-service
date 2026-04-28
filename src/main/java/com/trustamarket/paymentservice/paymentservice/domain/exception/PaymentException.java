package com.trustamarket.paymentservice.paymentservice.domain.exception;

import com.trustamarket.common.exception.CustomException;
import com.trustamarket.common.exception.ErrorCodeSpec;

public class PaymentException extends CustomException {
    private final ErrorCodeSpec errorCode;

    public PaymentException(ErrorCodeSpec errorCode) {
        super(errorCode);
        this.errorCode = errorCode;
    }
}

package com.trustamarket.paymentservice.paymentservice.domain.exception;

import com.trustamarket.common.exception.ErrorCodeSpec;
import org.springframework.http.HttpStatus;

public enum PayoutErrorCode implements ErrorCodeSpec {

    INVALID_PAYOUT_STATUS("INVALID_PAYOUT_STATUS","출금 상태 전이 오류입니다.", HttpStatus.CONFLICT, null);

    private final String code;
    private final String field;
    private final String message;
    private final HttpStatus status;

    PayoutErrorCode(String code, String message, HttpStatus status, String field) {
        this.code = code;
        this.message = message;
        this.status = status;
        this.field = field;
    }

    @Override public String getCode() { return code; }
    @Override public HttpStatus getStatus() { return status; }
    @Override public String getMessage() { return message; }
    @Override public String getField() { return field; }
}

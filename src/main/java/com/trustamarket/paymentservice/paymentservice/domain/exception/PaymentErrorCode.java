package com.trustamarket.paymentservice.paymentservice.domain.exception;

import com.trustamarket.common.exception.ErrorCodeSpec;
import org.springframework.http.HttpStatus;

public enum PaymentErrorCode implements ErrorCodeSpec {

    PAYMENT_NOT_FOUND("PAYMENT_NOT_FOUND", "결제를 찾을 수 없습니다.", HttpStatus.NOT_FOUND, "payment"),
    DUPLICATE_CHARGE_ID("DUPLICATE_CHARGE_ID","이미 결제가 존재하는 충전 요청입니다.", HttpStatus.CONFLICT, null),
    INVALID_PAYMENT_STATUS("INVALID_PAYMENT_STATUS","결제 상태 전이 오류입니다.", HttpStatus.CONFLICT, null),
    PAYMENT_AMOUNT_MISMATCH("PAYMENT_AMOUNT_MISMATCH","PG 승인 금액이 일치하지 않습니다.", HttpStatus.BAD_REQUEST, null);

    private final String code;
    private final String field;
    private final String message;
    private final HttpStatus status;

    PaymentErrorCode(String code, String message, HttpStatus status, String field) {
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

package com.trustamarket.paymentservice.paymentservice.payment.domain.exception;

import com.trustamarket.common.exception.ErrorCodeSpec;
import org.springframework.http.HttpStatus;

public enum PaymentErrorCode implements ErrorCodeSpec {

    PAYMENT_NOT_FOUND("PAYMENT_NOT_FOUND", "결제를 찾을 수 없습니다.", HttpStatus.NOT_FOUND, "payment"),
    DUPLICATE_CHARGE_ID("DUPLICATE_CHARGE_ID","이미 결제가 존재하는 충전 요청입니다.", HttpStatus.CONFLICT, null),
    INVALID_PAYMENT_STATUS("INVALID_PAYMENT_STATUS","결제 상태 전이 오류입니다.", HttpStatus.CONFLICT, null),
    REQUEST_ID_REQUIRED("REQUEST_ID_REQUIRED", "요청 ID는 필수값입니다.", HttpStatus.BAD_REQUEST, "pointTxRequestHistoryId"),
    USER_ID_REQUIRED("USER_ID_REQUIRED", "사용자 ID는 필수값입니다.", HttpStatus.BAD_REQUEST, "userId"),
    INVALID_PAYMENT_AMOUNT("INVALID_PAYMENT_AMOUNT", "결제 금액은 0 이상이어야 합니다.", HttpStatus.BAD_REQUEST, "amount"),
    INVALID_PAYMENT_KEY("INVALID_PAYMENT_KEY", "결제키가 유효하지 않습니다.", HttpStatus.BAD_REQUEST, null),
    PAYMENT_AMOUNT_MISMATCH("PAYMENT_AMOUNT_MISMATCH","PG 승인 금액이 일치하지 않습니다.", HttpStatus.BAD_REQUEST, null),
    PAYMENT_ACCESS_DENIED("PAYMENT_ACCESS_DENIED", "해당 결제에 대한 접근 권한이 없습니다.", HttpStatus.FORBIDDEN, null),
    PAYMENT_CONFIRM_UNKNOWN("PAYMENT_CONFIRM_UNKNOWN","결제 승인 결과를 확인할 수 없습니다.", HttpStatus.INTERNAL_SERVER_ERROR, null),
    FAILED_POINT_TO_WALLET("FAILED_POINT_TO_WALLET","충전 포인트 반영에 실패했습니다.", HttpStatus.INTERNAL_SERVER_ERROR, null);

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

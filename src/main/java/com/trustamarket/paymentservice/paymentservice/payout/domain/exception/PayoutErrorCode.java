package com.trustamarket.paymentservice.paymentservice.payout.domain.exception;

import com.trustamarket.common.exception.ErrorCodeSpec;
import org.springframework.http.HttpStatus;

public enum PayoutErrorCode implements ErrorCodeSpec {

    INVALID_USER_ID("INVALID_USER_ID", "userId는 필수값입니다.", HttpStatus.BAD_REQUEST, null),
    INVALID_POINT_TX_HISTORY_ID("INVALID_POINT_TX_HISTORY_ID", "pointTxHistoryId는 필수값입니다.", HttpStatus.BAD_REQUEST, null),
    INVALID_WITHDRAW_AMOUNT("INVALID_WITHDRAW_AMOUNT", "출금액은 0보다 커야합니다.", HttpStatus.BAD_REQUEST, null),
    DUPLICATE_PAYOUT_REQUEST("DUPLICATE_PAYOUT_REQUEST", "중복된 출금 요청입니다.", HttpStatus.CONFLICT, null),
    DUPLICATE_WITHDRAW_ID("DUPLICATE_WITHDRAW_ID","이미 출금이 존재하는 요청입니다.", HttpStatus.CONFLICT, null),
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

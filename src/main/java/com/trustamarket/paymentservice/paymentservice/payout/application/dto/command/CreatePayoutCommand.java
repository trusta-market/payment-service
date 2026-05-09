package com.trustamarket.paymentservice.paymentservice.payout.application.dto.command;

import com.trustamarket.paymentservice.paymentservice.payout.domain.exception.PayoutErrorCode;
import com.trustamarket.paymentservice.paymentservice.payout.domain.exception.PayoutException;

import java.util.UUID;

public record CreatePayoutCommand (
        UUID userId,
        UUID pointTxHistoryId,
        long withdrawAmount
) {
    public CreatePayoutCommand {
        if(userId == null){
            throw new PayoutException(PayoutErrorCode.INVALID_USER_ID);
        }
        if(pointTxHistoryId == null) {
            throw new PayoutException(PayoutErrorCode.INVALID_POINT_TX_HISTORY_ID);
        }
        if(withdrawAmount <= 0){
            throw new PayoutException(PayoutErrorCode.INVALID_WITHDRAW_AMOUNT);
        }
    }
}

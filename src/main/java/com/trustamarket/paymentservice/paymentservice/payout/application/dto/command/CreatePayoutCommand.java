package com.trustamarket.paymentservice.paymentservice.payout.application.dto.command;

import com.trustamarket.paymentservice.paymentservice.payout.domain.exception.PayoutErrorCode;
import com.trustamarket.paymentservice.paymentservice.payout.domain.exception.PayoutException;
import com.trustamarket.paymentservice.paymentservice.payout.domain.vo.Amount;

import java.util.UUID;

public record CreatePayoutCommand (
        UUID userId,
        UUID pointTxRequestHistoryId,
        Amount withdrawAmount
) {
    public CreatePayoutCommand {
        if(userId == null){
            throw new PayoutException(PayoutErrorCode.INVALID_USER_ID);
        }
        if(pointTxRequestHistoryId == null) {
            throw new PayoutException(PayoutErrorCode.INVALID_POINT_TX_HISTORY_ID);
        }
    }
}

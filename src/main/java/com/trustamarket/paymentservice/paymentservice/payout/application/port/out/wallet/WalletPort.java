package com.trustamarket.paymentservice.paymentservice.payout.application.port.out.wallet;

import com.trustamarket.common.response.CommonResponse;

public interface WalletPort {
    CommonResponse<Void> payoutCompleted(PayoutCompletedResult result);
}

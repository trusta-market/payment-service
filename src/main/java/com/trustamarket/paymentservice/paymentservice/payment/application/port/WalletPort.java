package com.trustamarket.paymentservice.paymentservice.payment.application.port;

import com.trustamarket.common.response.CommonResponse;

public interface WalletPort {
    CommonResponse<Void> pointToWallet(PaymentResponseResult result);
}
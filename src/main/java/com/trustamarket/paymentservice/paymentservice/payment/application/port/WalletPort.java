package com.trustamarket.paymentservice.paymentservice.payment.application.port;

import com.trustamarket.common.response.CommonResponse;
import org.springframework.http.ResponseEntity;

public interface WalletPort {
    ResponseEntity<CommonResponse<Void>> pointToWallet(PaymentResponseResult result);
}
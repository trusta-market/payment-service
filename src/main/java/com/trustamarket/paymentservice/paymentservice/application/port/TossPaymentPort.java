package com.trustamarket.paymentservice.paymentservice.application.port;

import com.trustamarket.paymentservice.paymentservice.application.dto.result.TossConfirmResult;

import java.util.UUID;

public interface TossPaymentPort {

    TossConfirmResult confirm(String paymentKey, UUID orderId, long amount);
}
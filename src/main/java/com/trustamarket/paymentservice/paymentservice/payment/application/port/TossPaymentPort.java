package com.trustamarket.paymentservice.paymentservice.payment.application.port;

import com.trustamarket.paymentservice.paymentservice.payment.application.dto.result.TossConfirmResult;

import java.util.UUID;

public interface TossPaymentPort {
    TossConfirmResult confirm(String paymentKey, UUID paymentId, long amount);
}
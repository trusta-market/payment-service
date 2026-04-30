package com.trustamarket.paymentservice.paymentservice.application.dto.result;

public record TossConfirmResult(
        String paymentKey,
        String orderId,
        String status,
        String method,
        long amount
) {}

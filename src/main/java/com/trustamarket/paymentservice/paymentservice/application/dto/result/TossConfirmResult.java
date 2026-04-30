package com.trustamarket.paymentservice.paymentservice.application.dto.result;

public record TossConfirmResult(
        String paymentId,
        String paymentKey,
        String status,
        String method,
        long amount
) {}

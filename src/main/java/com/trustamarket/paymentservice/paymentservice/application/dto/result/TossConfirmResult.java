package com.trustamarket.paymentservice.paymentservice.application.dto.result;

public record TossConfirmResult(
        String paymentKey,
        String paymentId,
        long amount
) {}

package com.trustamarket.paymentservice.paymentservice.payment.application.dto.result;

public record TossConfirmResult(
        String paymentKey,
        String paymentId,
        long amount
) {}

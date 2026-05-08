package com.trustamarket.paymentservice.paymentservice.payment.infrastructure.toss.dto;

public record TossConfirmRequest(
        String paymentKey,
        String orderId,
        long amount
) {}

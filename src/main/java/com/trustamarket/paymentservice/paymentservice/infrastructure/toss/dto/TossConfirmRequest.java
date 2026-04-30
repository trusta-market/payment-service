package com.trustamarket.paymentservice.paymentservice.infrastructure.toss.dto;

public record TossConfirmRequest(
        String paymentKey,
        String orderId, //chargeId
        long amount
) {
}

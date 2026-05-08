package com.trustamarket.paymentservice.paymentservice.payment.infrastructure.toss.dto;

public record TossConfirmResponse(
        String paymentKey,
        String orderId,
        String status,
        Long totalAmount
) {}

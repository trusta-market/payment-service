package com.trustamarket.paymentservice.paymentservice.payment.application.dto.command;

import java.util.UUID;

public record SucceededPaymentCommand(
        UUID paymentId,
        String paymentKey,
        long amount
){}
package com.trustamarket.paymentservice.paymentservice.payment.application.dto.command;

import java.util.UUID;

public record FailPaymentCommand (
        UUID paymentId,
        String code,
        String message
) {}

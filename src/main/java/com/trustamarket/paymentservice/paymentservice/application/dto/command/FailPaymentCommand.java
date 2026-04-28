package com.trustamarket.paymentservice.paymentservice.application.dto.command;

import java.util.UUID;

public record FailPaymentCommand (
        UUID paymentId,
        String code,
        String message
) {}

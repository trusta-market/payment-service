package com.trustamarket.paymentservice.paymentservice.application.dto.command;

import java.util.UUID;

public record PaymentDetailCommand (
        UUID userId,
        UUID paymentId
) {}

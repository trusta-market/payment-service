package com.trustamarket.paymentservice.paymentservice.application.dto.command;

import java.util.UUID;

public record CreatePaymentCommand (
        UUID chargeId,
        long amount
){}

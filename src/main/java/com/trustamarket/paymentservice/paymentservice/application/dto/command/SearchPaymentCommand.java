package com.trustamarket.paymentservice.paymentservice.application.dto.command;

import java.util.UUID;

public record SearchPaymentCommand (
        UUID userId,
        UUID paymentId,
        String keyword,
        long amount
        //뭐 필요한거 더 있지.. keyword, status, amount
) {}

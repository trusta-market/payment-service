package com.trustamarket.paymentservice.paymentservice.payout.presentation.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.UUID;

public record CreatePayoutRequest (
    @NotNull UUID userId,
    @NotNull UUID pointTxHistoryId,
    @Positive long withdrawAmount
) {}

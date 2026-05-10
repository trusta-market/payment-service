package com.trustamarket.paymentservice.paymentservice.payout.infrastructure.user.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.UUID;

public record UserAccountResponse (
    @NotNull UUID accountId,
    @NotNull String bankCode,
    @NotNull String accountNumber,
    @NotNull String accountHolder,
    @NotNull String accountType,
    @Positive boolean isVerified
) {}


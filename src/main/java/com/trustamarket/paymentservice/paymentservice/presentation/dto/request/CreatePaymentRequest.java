package com.trustamarket.paymentservice.paymentservice.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.UUID;

public record CreatePaymentRequest (
        @NotNull UUID chargeId,
        @Positive long amount
) {}

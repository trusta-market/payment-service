package com.trustamarket.paymentservice.paymentservice.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

import java.util.UUID;

public record CreatePaymentRequest (
        @NotBlank UUID chargeId,
        @Positive long amount
) {}

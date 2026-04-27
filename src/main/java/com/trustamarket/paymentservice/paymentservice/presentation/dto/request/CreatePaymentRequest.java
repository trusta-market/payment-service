package com.trustamarket.paymentservice.paymentservice.presentation.dto.request;

import jakarta.validation.constraints.Positive;

public record CreatePaymentRequest (
        @Positive long amount
) {}

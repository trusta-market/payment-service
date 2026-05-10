package com.trustamarket.paymentservice.paymentservice.payout.presentation.dto.request;

import com.trustamarket.paymentservice.paymentservice.payout.domain.vo.Amount;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.UUID;

public record CreatePayoutRequest (
    @NotNull UUID userId,
    @NotNull UUID pointTxRequestHistoryId,
    @Positive Amount withdrawAmount
) {}

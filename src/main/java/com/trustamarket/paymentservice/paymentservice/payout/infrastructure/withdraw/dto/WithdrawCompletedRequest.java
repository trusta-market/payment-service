package com.trustamarket.paymentservice.paymentservice.payout.infrastructure.withdraw.dto;

import com.trustamarket.paymentservice.paymentservice.payout.domain.enums.PayoutStatus;

import java.util.UUID;

public record WithdrawCompletedRequest (
        UUID userId,
        UUID payoutId,
        UUID pointTxRequestHistoryId,
        PayoutStatus  payoutStatus,
        long payoutAmount
) {}
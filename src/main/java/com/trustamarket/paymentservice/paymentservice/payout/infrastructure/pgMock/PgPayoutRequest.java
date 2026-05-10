package com.trustamarket.paymentservice.paymentservice.payout.infrastructure.pgMock;

import java.util.UUID;

public record PgPayoutRequest (
        UUID payoutId,
        long amount
) {}

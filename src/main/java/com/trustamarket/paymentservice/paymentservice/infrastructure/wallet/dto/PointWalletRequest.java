package com.trustamarket.paymentservice.paymentservice.infrastructure.wallet.dto;

import java.util.UUID;

public record PointWalletRequest (
    UUID paymentId,
    long chargedAmount
){}

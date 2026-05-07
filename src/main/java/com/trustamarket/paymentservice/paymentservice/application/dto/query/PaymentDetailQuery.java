package com.trustamarket.paymentservice.paymentservice.application.dto.query;

import java.util.UUID;

public record PaymentDetailQuery(
        UUID paymentId,
        UUID userId
) {}

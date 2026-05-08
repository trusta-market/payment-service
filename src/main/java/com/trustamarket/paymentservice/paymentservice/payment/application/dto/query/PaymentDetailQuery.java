package com.trustamarket.paymentservice.paymentservice.payment.application.dto.query;

import java.util.UUID;

public record PaymentDetailQuery(
        UUID paymentId,
        UUID userId
) {}

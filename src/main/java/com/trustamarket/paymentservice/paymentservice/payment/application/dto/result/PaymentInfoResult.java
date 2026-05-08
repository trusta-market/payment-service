package com.trustamarket.paymentservice.paymentservice.payment.application.dto.result;

import java.util.UUID;

public record PaymentInfoResult (
    UUID paymentId,
    long amount
) {}

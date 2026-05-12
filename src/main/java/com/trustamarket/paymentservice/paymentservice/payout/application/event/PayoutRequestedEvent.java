package com.trustamarket.paymentservice.paymentservice.payout.application.event;

import java.util.UUID;

public record PayoutRequestedEvent(UUID payoutId) {}

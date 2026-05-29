package com.trustamarket.paymentservice.paymentservice.payout.infrastructure.notification;

public interface PayoutNotificationClient {
    void send(String message);
}

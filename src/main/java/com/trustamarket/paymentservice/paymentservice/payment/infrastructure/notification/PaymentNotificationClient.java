package com.trustamarket.paymentservice.paymentservice.payment.infrastructure.notification;

public interface PaymentNotificationClient {
    void send(String message);
}

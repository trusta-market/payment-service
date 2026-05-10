package com.trustamarket.paymentservice.paymentservice.payout.application.port.out.user;

public record UserAccount (
        String bankCode,
        String accountNumber,
        String accountHolder,
        boolean isVerified
) {}

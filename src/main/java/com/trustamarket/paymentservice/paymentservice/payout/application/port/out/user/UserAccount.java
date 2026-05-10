package com.trustamarket.paymentservice.paymentservice.payout.application.port.out.user;

public record UserAccount (
        String bankCode,
        String accountNumber,
        String accountHolder,
        boolean isVerified
) {
    public UserAccount {
        if (bankCode == null || bankCode.isBlank()) {
            throw new IllegalArgumentException("bankCode must not be blank");
        }
        if (accountNumber == null || accountNumber.isBlank()) {
            throw new IllegalArgumentException("accountNumber must not be blank");
        }
        if (accountHolder == null || accountHolder.isBlank()) {
            throw new IllegalArgumentException("accountHolder must not be blank");
        }
    }
}

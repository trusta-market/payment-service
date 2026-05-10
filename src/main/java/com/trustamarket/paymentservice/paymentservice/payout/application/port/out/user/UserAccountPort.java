package com.trustamarket.paymentservice.paymentservice.payout.application.port.out.user;

import java.util.UUID;

public interface UserAccountPort {
    UserAccount getUserAccount(UUID userId);
}

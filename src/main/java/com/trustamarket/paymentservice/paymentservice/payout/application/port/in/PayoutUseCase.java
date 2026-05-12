package com.trustamarket.paymentservice.paymentservice.payout.application.port.in;

import com.trustamarket.paymentservice.paymentservice.payout.application.dto.command.CreatePayoutCommand;
import com.trustamarket.paymentservice.paymentservice.payout.application.dto.result.CreatePayoutResult;

public interface PayoutUseCase {
    CreatePayoutResult createPayout(CreatePayoutCommand command);
}

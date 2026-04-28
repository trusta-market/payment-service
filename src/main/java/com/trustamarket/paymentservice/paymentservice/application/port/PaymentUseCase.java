package com.trustamarket.paymentservice.paymentservice.application.port;

import com.trustamarket.paymentservice.paymentservice.application.dto.command.CreatePaymentCommand;
import com.trustamarket.paymentservice.paymentservice.application.dto.result.CreatePaymentResult;

public interface PaymentUseCase {
    CreatePaymentResult createPayment(CreatePaymentCommand command);
}
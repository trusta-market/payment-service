package com.trustamarket.paymentservice.paymentservice.application.port;

import com.trustamarket.paymentservice.paymentservice.application.dto.command.CreatePaymentCommand;
import com.trustamarket.paymentservice.paymentservice.application.dto.command.FailPaymentCommand;
import com.trustamarket.paymentservice.paymentservice.application.dto.command.SucceededPaymentCommand;
import com.trustamarket.paymentservice.paymentservice.application.dto.result.CreatePaymentResult;
import com.trustamarket.paymentservice.paymentservice.application.dto.result.FailPaymentResult;
import com.trustamarket.paymentservice.paymentservice.application.dto.result.SucceededPaymentResult;

public interface PaymentUseCase {
    CreatePaymentResult createPayment(CreatePaymentCommand command);
    SucceededPaymentResult succeededPayment(SucceededPaymentCommand command);
    FailPaymentResult failPayment(FailPaymentCommand command);
}
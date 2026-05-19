package com.trustamarket.paymentservice.paymentservice.payment.application.port;

import com.trustamarket.paymentservice.paymentservice.payment.application.dto.command.SucceededPaymentCommand;
import com.trustamarket.paymentservice.paymentservice.payment.application.dto.result.SucceededPaymentResult;

public interface PaymentFacadeUseCase {
    SucceededPaymentResult succeededPayment(SucceededPaymentCommand command);
}
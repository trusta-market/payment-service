package com.trustamarket.paymentservice.paymentservice.payment.application.service;

import com.trustamarket.paymentservice.paymentservice.payment.application.dto.command.FailPaymentCommand;
import com.trustamarket.paymentservice.paymentservice.payment.application.dto.command.SucceededPaymentCommand;
import com.trustamarket.paymentservice.paymentservice.payment.application.dto.result.SucceededPaymentResult;
import com.trustamarket.paymentservice.paymentservice.payment.application.port.PaymentFacadeUseCase;
import com.trustamarket.paymentservice.paymentservice.payment.application.port.PaymentResponseResult;
import com.trustamarket.paymentservice.paymentservice.payment.application.port.TossPaymentPort;
import com.trustamarket.paymentservice.paymentservice.payment.application.port.WalletPort;
import com.trustamarket.paymentservice.paymentservice.payment.domain.entity.Payment;
import com.trustamarket.paymentservice.paymentservice.payment.domain.exception.PaymentException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentFacade implements PaymentFacadeUseCase {

    private final PaymentService paymentService;
    private final TossPaymentPort tossPaymentPort;
    private final WalletPort walletPort;

    public SucceededPaymentResult succeededPayment(SucceededPaymentCommand command) {

        try {
            tossPaymentPort.confirm(
                    command.paymentKey(),
                    command.paymentId(),
                    command.amount()
            );
        } catch (PaymentException e) {
            paymentService.markFail(new FailPaymentCommand(
                    command.paymentId(), "CONFIRM_FAIL", e.getMessage()
            ));
            throw e;
        }

        Payment payment = paymentService.markSuccess(command);
        SucceededPaymentResult result = SucceededPaymentResult.from(payment);

        walletPort.pointToWallet(PaymentResponseResult.from(payment));

        return result;
    }
}

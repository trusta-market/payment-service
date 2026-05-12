package com.trustamarket.paymentservice.paymentservice.payment.presentation;

import com.trustamarket.common.response.CommonResponse;
import com.trustamarket.paymentservice.paymentservice.payment.application.dto.command.CreatePaymentCommand;
import com.trustamarket.paymentservice.paymentservice.payment.application.port.PaymentUseCase;
import com.trustamarket.paymentservice.paymentservice.payment.presentation.dto.request.CreatePaymentRequest;
import com.trustamarket.paymentservice.paymentservice.payment.domain.vo.Amount;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/internal/v1/payments")
@RequiredArgsConstructor
public class PaymentInternalController {

    private final PaymentUseCase paymentUseCase;

    @PostMapping("/charges")
    public CommonResponse<Void> createPayment(@Valid @RequestBody CreatePaymentRequest request) {

        CreatePaymentCommand command = new CreatePaymentCommand(request.userId(), request.pointTxRequestHistoryId(), Amount.of(request.chargeAmount()));
        paymentUseCase.createPayment(command);

        return new CommonResponse<>(HttpStatus.NO_CONTENT.value(), null);
    }
}
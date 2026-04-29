package com.trustamarket.paymentservice.paymentservice.presentation;

import com.trustamarket.paymentservice.paymentservice.application.dto.command.CreatePaymentCommand;
import com.trustamarket.paymentservice.paymentservice.application.dto.command.FailPaymentCommand;
import com.trustamarket.paymentservice.paymentservice.application.dto.command.SucceededPaymentCommand;
import com.trustamarket.paymentservice.paymentservice.application.dto.result.CreatePaymentResult;
import com.trustamarket.paymentservice.paymentservice.application.dto.result.FailPaymentResult;
import com.trustamarket.paymentservice.paymentservice.application.dto.result.SucceededPaymentResult;
import com.trustamarket.paymentservice.paymentservice.application.port.PaymentUseCase;
import com.trustamarket.paymentservice.paymentservice.presentation.dto.request.CreatePaymentRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.UUID;

@Controller
@RequestMapping("/demo/payment")
@RequiredArgsConstructor
public class DemoPaymentController {

    private final PaymentUseCase paymentUseCase;

    @PostMapping
    @ResponseBody
    public CreatePaymentResult createPayment(@Valid @RequestBody CreatePaymentRequest request) {
        CreatePaymentCommand command = new CreatePaymentCommand(request.chargeId(), request.amount());

        return paymentUseCase.createPayment(command);
    }

    @GetMapping("/{paymentId}/success")
    @ResponseBody
    public SucceededPaymentResult paymentSuccess(
            @PathVariable UUID paymentId,
            @RequestParam @NotBlank String paymentKey,
            @RequestParam @Positive long amount
    ) {
        SucceededPaymentCommand command = new SucceededPaymentCommand(paymentId, paymentKey, amount);
        return paymentUseCase.succeededPayment(command);
    }

    @GetMapping("/{paymentId}/fail")
    @ResponseBody
    public FailPaymentResult paymentFail(
            @PathVariable UUID paymentId,
            @RequestParam @NotBlank String code,
            @RequestParam @NotBlank String message
    ) {
        FailPaymentCommand command = new FailPaymentCommand(paymentId, code, message);
        return paymentUseCase.failPayment(command);
    }
}
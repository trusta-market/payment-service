package com.trustamarket.paymentservice.paymentservice.presentation;

import com.trustamarket.common.response.CommonResponse;
import com.trustamarket.paymentservice.paymentservice.application.dto.command.CreatePaymentCommand;
import com.trustamarket.paymentservice.paymentservice.application.dto.command.FailPaymentCommand;
import com.trustamarket.paymentservice.paymentservice.application.dto.command.SucceededPaymentCommand;
import com.trustamarket.paymentservice.paymentservice.application.dto.result.CreatePaymentResult;
import com.trustamarket.paymentservice.paymentservice.application.dto.result.FailPaymentResult;
import com.trustamarket.paymentservice.paymentservice.application.dto.result.SucceededPaymentResult;
import com.trustamarket.paymentservice.paymentservice.application.port.PaymentUseCase;
import com.trustamarket.paymentservice.paymentservice.presentation.dto.request.CreatePaymentRequest;
import com.trustamarket.paymentservice.paymentservice.presentation.dto.response.CreatePaymentResponse;
import com.trustamarket.paymentservice.paymentservice.presentation.dto.response.FailPaymentResponse;
import com.trustamarket.paymentservice.paymentservice.presentation.dto.response.SucceededPaymentResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentUseCase paymentUseCase;

    @PostMapping
    public ResponseEntity<CommonResponse<CreatePaymentResponse>> createPayment(@Valid @RequestBody CreatePaymentRequest request) {
        CreatePaymentCommand command = new CreatePaymentCommand(request.chargeId(), request.amount());

        CreatePaymentResult result = paymentUseCase.createPayment(command);

        CreatePaymentResponse response = CreatePaymentResponse.from(result);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new CommonResponse<>(HttpStatus.CREATED.value(), response));
    }

    @PostMapping("/{paymentId}/success")
    public CommonResponse<SucceededPaymentResponse> successPayment(
            @PathVariable UUID paymentId,
            @RequestParam @NotBlank String paymentKey,
            @RequestParam @Positive long amount
    ){
        SucceededPaymentCommand command = new SucceededPaymentCommand(paymentId, paymentKey, amount);

        SucceededPaymentResult result = paymentUseCase.succeededPayment(command);

        SucceededPaymentResponse response = SucceededPaymentResponse.from(result);

        return new CommonResponse<>(HttpStatus.OK.value(), response);
    }

    @PostMapping("/{paymentId}/failure")
    public CommonResponse<FailPaymentResponse> failPayment(
            @PathVariable UUID paymentId,
            @RequestParam @NotBlank String code,
            @RequestParam @NotBlank String message
    ) {
        FailPaymentCommand command = new FailPaymentCommand(paymentId, code, message);

        FailPaymentResult result = paymentUseCase.failPayment(command);

        FailPaymentResponse response = FailPaymentResponse.from(result);

        return new CommonResponse<>(HttpStatus.OK.value(), response);
    }
}

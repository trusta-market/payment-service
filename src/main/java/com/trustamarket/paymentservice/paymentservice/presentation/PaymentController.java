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
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
    public CommonResponse<CreatePaymentResponse> createPayment(@Valid @RequestBody CreatePaymentRequest request) {
        CreatePaymentCommand command = new CreatePaymentCommand(request.chargeId(), request.amount());

        CreatePaymentResult result = paymentUseCase.createPayment(command);

        CreatePaymentResponse response = new CreatePaymentResponse(
                result.paymentId(),
                result.chargeId(),
                result.paymentStatus().name(),
                result.amount(),
                result.createdAt()
        );

        return new CommonResponse<>(HttpStatus.CREATED.value(), response);
    }

    @PostMapping("/{paymentId}/success")
    public CommonResponse<SucceededPaymentResponse> successPayment(
            @PathVariable UUID paymentId,
            @RequestParam String paymentKey,
            @RequestParam long amount
    ){
        SucceededPaymentCommand command = new SucceededPaymentCommand(paymentId, paymentKey, amount);

        SucceededPaymentResult result = paymentUseCase.succeededPayment(command);

        SucceededPaymentResponse response = new SucceededPaymentResponse(
                result.paymentId(),
                result.paymentStatus(),
                result.amount(),
                result.updatedAt()
        );

        return new CommonResponse<>(HttpStatus.OK.value(), response);
    }

    @PostMapping("/{paymentId}/failure")
    public CommonResponse<FailPaymentResponse> failPayment(
            @PathVariable UUID paymentId,
            @RequestParam String code,
            @RequestParam String message
    ){
        FailPaymentCommand command = new FailPaymentCommand(paymentId, code, message);

        FailPaymentResult result = paymentUseCase.failPayment(command);

        FailPaymentResponse response = new FailPaymentResponse(
                result.paymentId(),
                result.paymentStatus(),
                result.updatedAt()
        );

        return new CommonResponse<>(HttpStatus.OK.value(), response);
    }
}

package com.trustamarket.paymentservice.paymentservice.presentation;

import com.trustamarket.paymentservice.paymentservice.application.dto.command.CreatePaymentCommand;
import com.trustamarket.paymentservice.paymentservice.application.port.PaymentUseCase;
import com.trustamarket.paymentservice.paymentservice.application.dto.result.CreatePaymentResult;
import com.trustamarket.paymentservice.paymentservice.presentation.dto.request.CreatePaymentRequest;
import com.trustamarket.paymentservice.paymentservice.presentation.dto.response.CreatePaymentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentUseCase paymentUseCase;

    @PostMapping
    public ResponseEntity<CreatePaymentResponse> createPayment(@RequestBody CreatePaymentRequest request) {
        CreatePaymentCommand command = new CreatePaymentCommand(request.chargeId(), request.amount());

        CreatePaymentResult result = paymentUseCase.createPayment(command);

        CreatePaymentResponse response = new CreatePaymentResponse(
                result.paymentId(),
                result.chargeId(),
                result.paymentStatus().name(),
                result.amount(),
                result.createdAt()
        );

        return ResponseEntity.ok(response);
    }
}

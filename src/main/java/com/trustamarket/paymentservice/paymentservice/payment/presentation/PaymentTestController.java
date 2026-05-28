package com.trustamarket.paymentservice.paymentservice.payment.presentation;

import com.trustamarket.common.response.CommonResponse;
import com.trustamarket.paymentservice.paymentservice.payment.application.dto.command.SucceededPaymentCommand;
import com.trustamarket.paymentservice.paymentservice.payment.application.dto.result.PaymentInfoResult;
import com.trustamarket.paymentservice.paymentservice.payment.application.dto.result.SucceededPaymentResult;
import com.trustamarket.paymentservice.paymentservice.payment.application.port.PaymentFacadeUseCase;
import com.trustamarket.paymentservice.paymentservice.payment.application.port.PaymentUseCase;
import com.trustamarket.paymentservice.paymentservice.payment.presentation.dto.response.SucceededPaymentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@Profile("mocktest")
@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
public class PaymentTestController {

    private final PaymentFacadeUseCase paymentFacade;
    private final PaymentUseCase paymentUseCase;

    @PostMapping("/{paymentId}/test")
    public ResponseEntity<CommonResponse<SucceededPaymentResponse>> testPayment(@PathVariable UUID paymentId){

        PaymentInfoResult info = paymentUseCase.getPaymentInfo(paymentId);
        SucceededPaymentCommand command = new SucceededPaymentCommand(
                paymentId,
                "mock-payment-key-" + paymentId,
                info.amount()
        );

        SucceededPaymentResult result = paymentFacade.succeededPayment(command);
        return ResponseEntity.ok(new CommonResponse<>(
                HttpStatus.OK.value(),
                SucceededPaymentResponse.from(result))
        );
    }
}

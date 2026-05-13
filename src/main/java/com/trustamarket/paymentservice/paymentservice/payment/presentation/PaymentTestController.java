package com.trustamarket.paymentservice.paymentservice.payment.presentation;

import com.trustamarket.common.response.CommonResponse;
import com.trustamarket.paymentservice.paymentservice.payment.application.dto.command.FailPaymentCommand;
import com.trustamarket.paymentservice.paymentservice.payment.application.dto.command.SucceededPaymentCommand;
import com.trustamarket.paymentservice.paymentservice.payment.application.dto.result.FailPaymentResult;
import com.trustamarket.paymentservice.paymentservice.payment.application.dto.result.PaymentInfoResult;
import com.trustamarket.paymentservice.paymentservice.payment.application.dto.result.SucceededPaymentResult;
import com.trustamarket.paymentservice.paymentservice.payment.application.port.PaymentUseCase;
import com.trustamarket.paymentservice.paymentservice.payment.presentation.dto.response.FailPaymentResponse;
import com.trustamarket.paymentservice.paymentservice.payment.presentation.dto.response.SucceededPaymentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
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

    private final PaymentUseCase paymentUseCase;

    @PostMapping("/{paymentId}/test")
    public CommonResponse<?> testPayment(@PathVariable UUID paymentId){
        boolean success = Math.random() > 0.05;

        if (success) {
            PaymentInfoResult info = paymentUseCase.getPaymentInfo(paymentId);
            SucceededPaymentCommand command = new SucceededPaymentCommand(
                    paymentId,
                    "mock-payment-key-" + paymentId,
                    info.amount()
            );

            SucceededPaymentResult result = paymentUseCase.succeededPayment(command);
            return new CommonResponse<>(
                    HttpStatus.OK.value(),
                    SucceededPaymentResponse.from(result)
            );
        }

        FailPaymentCommand command = new FailPaymentCommand(
                paymentId,
                "MOCK_PAYMENT_FAILED",
                "부하테스트용 mock 결제 실패"
        );

        FailPaymentResult result = paymentUseCase.failPayment(command);
        return new CommonResponse<>(
                HttpStatus.OK.value(),
                FailPaymentResponse.from(result)
        );
    }

}

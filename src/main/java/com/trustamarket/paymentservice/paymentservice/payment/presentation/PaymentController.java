package com.trustamarket.paymentservice.paymentservice.payment.presentation;

import com.trustamarket.common.response.CommonResponse;
import com.trustamarket.common.response.SlicedResponse;
import com.trustamarket.common.util.SecurityUtil;
import com.trustamarket.paymentservice.paymentservice.payment.application.dto.command.FailPaymentCommand;
import com.trustamarket.paymentservice.paymentservice.payment.application.dto.command.SucceededPaymentCommand;
import com.trustamarket.paymentservice.paymentservice.payment.application.dto.query.PaymentDetailQuery;
import com.trustamarket.paymentservice.paymentservice.payment.application.dto.query.PaymentSearchQuery;
import com.trustamarket.paymentservice.paymentservice.payment.application.dto.result.FailPaymentResult;
import com.trustamarket.paymentservice.paymentservice.payment.application.dto.result.PaymentDetailResult;
import com.trustamarket.paymentservice.paymentservice.payment.application.dto.result.PaymentInfoResult;
import com.trustamarket.paymentservice.paymentservice.payment.application.dto.result.SearchPaymentResult;
import com.trustamarket.paymentservice.paymentservice.payment.application.dto.result.SucceededPaymentResult;
import com.trustamarket.paymentservice.paymentservice.payment.application.port.PaymentUseCase;
import com.trustamarket.paymentservice.paymentservice.payment.presentation.dto.request.SearchPaymentRequest;
import com.trustamarket.paymentservice.paymentservice.payment.presentation.dto.response.FailPaymentResponse;
import com.trustamarket.paymentservice.paymentservice.payment.presentation.dto.response.PaymentDetailResponse;
import com.trustamarket.paymentservice.paymentservice.payment.presentation.dto.response.SearchPaymentResponse;
import com.trustamarket.paymentservice.paymentservice.payment.presentation.dto.response.SucceededPaymentResponse;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentUseCase paymentUseCase;

    @GetMapping("/{paymentId}/success")
    public CommonResponse<SucceededPaymentResponse> successPayment(
            @PathVariable @NotNull UUID paymentId,
            @RequestParam @NotBlank String paymentKey,
            @RequestParam @Positive long amount
    ){
        SucceededPaymentCommand command = new SucceededPaymentCommand(paymentId, paymentKey, amount);
        SucceededPaymentResult result = paymentUseCase.succeededPayment(command);
        SucceededPaymentResponse response = SucceededPaymentResponse.from(result);

        return new CommonResponse<>(HttpStatus.OK.value(), response);
    }

    @GetMapping("/{paymentId}/failure")
    public CommonResponse<FailPaymentResponse> failPayment(
            @PathVariable @NotNull UUID paymentId,
            @RequestParam @NotBlank String code,
            @RequestParam @NotBlank String message
    ) {

        FailPaymentCommand command = new FailPaymentCommand(paymentId, code, message);
        FailPaymentResult result = paymentUseCase.failPayment(command);
        FailPaymentResponse response = FailPaymentResponse.from(result);

        return new CommonResponse<>(HttpStatus.OK.value(), response);
    }

    //테스트 목적
    @Profile("mocktest")
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

    @PreAuthorize("hasRole('MEMBER')")
    @GetMapping("/{paymentId}")
    public CommonResponse<PaymentDetailResponse> paymentDetail(
            @PathVariable @NotNull UUID paymentId
    ){
        UUID userId = SecurityUtil.getCurrentUserIdOrThrow();

        PaymentDetailQuery command = new PaymentDetailQuery(paymentId, userId);
        PaymentDetailResult result = paymentUseCase.getPaymentDetail(command);
        PaymentDetailResponse response = PaymentDetailResponse.from(result);

        return new CommonResponse<>(HttpStatus.OK.value(), response);
    }

    @PreAuthorize("hasRole('MEMBER')")
    @GetMapping()
    public SlicedResponse<SearchPaymentResponse> searchPayments(
            @ModelAttribute SearchPaymentRequest request,
            Pageable pageable
    ){
        UUID userId =  SecurityUtil.getCurrentUserIdOrThrow();

        PaymentSearchQuery query = new PaymentSearchQuery(
                request.paymentId(),
                userId,
                request.minAmount(),
                request.maxAmount(),
                request.status()
        );
        Slice<SearchPaymentResult> result = paymentUseCase.searchPayments(query, pageable);
        Slice<SearchPaymentResponse> response = result.map(SearchPaymentResponse::from);

        return SlicedResponse.of(HttpStatus.OK.value(), response);
    }
}

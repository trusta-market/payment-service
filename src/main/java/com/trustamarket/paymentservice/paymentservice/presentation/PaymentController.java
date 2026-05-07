package com.trustamarket.paymentservice.paymentservice.presentation;

import com.trustamarket.common.response.CommonResponse;
import com.trustamarket.common.response.SlicedResponse;
import com.trustamarket.common.util.SecurityUtil;
import com.trustamarket.paymentservice.paymentservice.application.dto.command.FailPaymentCommand;
import com.trustamarket.paymentservice.paymentservice.application.dto.query.PaymentDetailQuery;
import com.trustamarket.paymentservice.paymentservice.application.dto.query.SearchPaymentQuery;
import com.trustamarket.paymentservice.paymentservice.application.dto.command.SucceededPaymentCommand;
import com.trustamarket.paymentservice.paymentservice.application.dto.result.FailPaymentResult;
import com.trustamarket.paymentservice.paymentservice.application.dto.result.PaymentDetailResult;
import com.trustamarket.paymentservice.paymentservice.application.dto.result.SearchPaymentResult;
import com.trustamarket.paymentservice.paymentservice.application.dto.result.SucceededPaymentResult;
import com.trustamarket.paymentservice.paymentservice.application.port.PaymentUseCase;
import com.trustamarket.paymentservice.paymentservice.presentation.dto.request.SearchPaymentRequest;
import com.trustamarket.paymentservice.paymentservice.presentation.dto.response.FailPaymentResponse;
import com.trustamarket.paymentservice.paymentservice.presentation.dto.response.PaymentDetailResponse;
import com.trustamarket.paymentservice.paymentservice.presentation.dto.response.SearchPaymentResponse;
import com.trustamarket.paymentservice.paymentservice.presentation.dto.response.SucceededPaymentResponse;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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

        SearchPaymentQuery query = new SearchPaymentQuery(
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

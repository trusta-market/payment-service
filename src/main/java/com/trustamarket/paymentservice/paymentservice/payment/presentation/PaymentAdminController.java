package com.trustamarket.paymentservice.paymentservice.payment.presentation;

import com.trustamarket.common.response.SlicedResponse;
import com.trustamarket.paymentservice.paymentservice.payment.application.dto.query.PaymentTxSearchQuery;
import com.trustamarket.paymentservice.paymentservice.payment.application.dto.result.SearchPaymentTxResult;
import com.trustamarket.paymentservice.paymentservice.payment.application.port.PaymentUseCase;
import com.trustamarket.paymentservice.paymentservice.payment.presentation.dto.request.SearchPaymentTxRequest;
import com.trustamarket.paymentservice.paymentservice.payment.presentation.dto.response.SearchPaymentTxResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin/payments")
@RequiredArgsConstructor
public class PaymentAdminController {

    private final PaymentUseCase paymentUseCase;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/transactions")
    public ResponseEntity<SlicedResponse<SearchPaymentTxResponse>> searchPaymentTxs(
            @ModelAttribute SearchPaymentTxRequest request,
            Pageable pageable
    ) {
        PaymentTxSearchQuery query = new PaymentTxSearchQuery(
                request.userId(),
                request.txType(),
                request.minAmount(),
                request.maxAmount(),
                request.pgResponseCode()
        );
        Slice<SearchPaymentTxResult> result = paymentUseCase.searchPaymentTxs(query, pageable);
        Slice<SearchPaymentTxResponse> response = result.map(SearchPaymentTxResponse::from);

        return ResponseEntity.ok(SlicedResponse.of(HttpStatus.OK.value(), response));
    }
}

package com.trustamarket.paymentservice.paymentservice.payout.presentation;

import com.trustamarket.common.response.CommonResponse;
import com.trustamarket.paymentservice.paymentservice.payout.application.dto.command.CreatePayoutCommand;
import com.trustamarket.paymentservice.paymentservice.payout.application.dto.result.CreatePayoutResult;
import com.trustamarket.paymentservice.paymentservice.payout.application.port.PayoutUseCase;
import com.trustamarket.paymentservice.paymentservice.payout.presentation.dto.request.CreatePayoutRequest;
import com.trustamarket.paymentservice.paymentservice.payout.presentation.dto.response.CreatePayoutResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/internal/v1/payouts")
@RequiredArgsConstructor
public class PayoutInternalController {

    private final PayoutUseCase payoutUseCase;

    @PostMapping
    public ResponseEntity<CommonResponse<CreatePayoutResponse>> createPayout(@Valid @RequestBody CreatePayoutRequest request){
        CreatePayoutCommand command = new CreatePayoutCommand(request.userId(), request.pointTxHistoryId(), request.withdrawAmount());
        CreatePayoutResult result = payoutUseCase.createPayout(command);
        CreatePayoutResponse response = CreatePayoutResponse.from(result);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new CommonResponse<>(HttpStatus.CREATED.value(), response));
    }
}

package com.trustamarket.paymentservice.paymentservice.payout.presentation;

import com.trustamarket.common.response.CommonResponse;
import com.trustamarket.paymentservice.paymentservice.payout.application.dto.command.CreatePayoutCommand;
import com.trustamarket.paymentservice.paymentservice.payout.application.port.in.PayoutUseCase;
import com.trustamarket.paymentservice.paymentservice.payout.presentation.dto.request.CreatePayoutRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
    public CommonResponse<Void> createPayout(@Valid @RequestBody CreatePayoutRequest request){
        CreatePayoutCommand command = new CreatePayoutCommand(request.userId(), request.pointTxRequestHistoryId(), request.withdrawAmount());
        payoutUseCase.createPayout(command);

        return new CommonResponse<>(HttpStatus.NO_CONTENT.value(), null);
    }
}

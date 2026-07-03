package com.trustamarket.paymentservice.paymentservice.payout.application.port.in;

import com.trustamarket.paymentservice.paymentservice.payout.application.dto.command.CreatePayoutCommand;
import com.trustamarket.paymentservice.paymentservice.payout.application.dto.query.PayoutSearchQuery;
import com.trustamarket.paymentservice.paymentservice.payout.application.dto.query.PayoutTxSearchQuery;
import com.trustamarket.paymentservice.paymentservice.payout.application.dto.result.CreatePayoutResult;
import com.trustamarket.paymentservice.paymentservice.payout.application.dto.result.SearchPayoutResult;
import com.trustamarket.paymentservice.paymentservice.payout.application.dto.result.SearchPayoutTxResult;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.UUID;

public interface PayoutUseCase {
    CreatePayoutResult createPayout(CreatePayoutCommand command);
    SearchPayoutResult getPayoutDetail(UUID payoutId, UUID userId);
    Slice<SearchPayoutResult> searchPayouts(PayoutSearchQuery query, Pageable pageable);
    Slice<SearchPayoutTxResult> searchPayoutTxs(PayoutTxSearchQuery query, Pageable pageable);
}

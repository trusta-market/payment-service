package com.trustamarket.paymentservice.paymentservice.payout.domain.repository;

import com.trustamarket.paymentservice.paymentservice.payout.application.dto.query.PayoutSearchQuery;
import com.trustamarket.paymentservice.paymentservice.payout.application.dto.query.PayoutTxSearchQuery;
import com.trustamarket.paymentservice.paymentservice.payout.domain.entity.Payout;
import com.trustamarket.paymentservice.paymentservice.payout.domain.entity.PayoutTx;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.UUID;

public interface PayoutRepository {
    Payout saveAndFlush(Payout payout);
    Payout findById(UUID payoutId);
    boolean existsByPointTxRequestHistoryId(UUID pointTxRequestHistoryId);
    Payout save(Payout payout);
    Slice<Payout> searchPayouts(PayoutSearchQuery query, Pageable pageable);
    Slice<PayoutTx> searchPayoutTxs(PayoutTxSearchQuery query, Pageable pageable);
}

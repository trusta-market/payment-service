package com.trustamarket.paymentservice.paymentservice.payout.application.service;

import com.trustamarket.paymentservice.paymentservice.payout.application.dto.command.CreatePayoutCommand;
import com.trustamarket.paymentservice.paymentservice.payout.application.dto.query.PayoutSearchQuery;
import com.trustamarket.paymentservice.paymentservice.payout.application.dto.query.PayoutTxSearchQuery;
import com.trustamarket.paymentservice.paymentservice.payout.application.dto.result.CreatePayoutResult;
import com.trustamarket.paymentservice.paymentservice.payout.application.dto.result.SearchPayoutResult;
import com.trustamarket.paymentservice.paymentservice.payout.application.dto.result.SearchPayoutTxResult;
import com.trustamarket.paymentservice.paymentservice.payout.application.event.PayoutRequestedEvent;
import com.trustamarket.paymentservice.paymentservice.payout.application.port.in.PayoutUseCase;
import com.trustamarket.paymentservice.paymentservice.payout.domain.entity.Payout;
import com.trustamarket.paymentservice.paymentservice.payout.domain.entity.PayoutTx;
import com.trustamarket.paymentservice.paymentservice.payout.domain.enums.PayoutStatus;
import com.trustamarket.paymentservice.paymentservice.payout.domain.exception.PayoutErrorCode;
import com.trustamarket.paymentservice.paymentservice.payout.domain.exception.PayoutException;
import com.trustamarket.paymentservice.paymentservice.payout.domain.repository.PayoutRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PayoutService implements PayoutUseCase {

    private final PayoutRepository payoutRepository;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    @Transactional
    public CreatePayoutResult createPayout(CreatePayoutCommand command) {
        try {
            Payout payout = Payout.create(
                    command.userId(),
                    command.pointTxRequestHistoryId(),
                    command.withdrawAmount()
            );
            Payout savedPayout = payoutRepository.saveAndFlush(payout);

            eventPublisher.publishEvent(new PayoutRequestedEvent(savedPayout.getPayoutId()));

            return CreatePayoutResult.from(savedPayout);
        } catch (DataIntegrityViolationException e) {
            throw new PayoutException(PayoutErrorCode.DUPLICATE_PAYOUT_REQUEST);
        }
    }

    @Transactional
    public Payout success(UUID payoutId){
        Payout payout = payoutRepository.findById(payoutId);
        if (payout.getStatus() != PayoutStatus.REQUESTED) {
            return payout;
        }

        payout.complete();
        return payout;
    }

    @Transactional
    public Payout fail(UUID payoutId, String reason){
        Payout payout = payoutRepository.findById(payoutId);
        if (payout.getStatus() != PayoutStatus.REQUESTED) {
            return payout;
        }

        payout.fail(reason);
        return payout;
    }

    @Override
    @Transactional(readOnly = true)
    public SearchPayoutResult getPayoutDetail(UUID payoutId, UUID userId) {
        Payout payout = payoutRepository.findById(payoutId);
        if (!payout.getUserId().equals(userId)) {
            throw new PayoutException(PayoutErrorCode.PAYOUT_NOT_FOUND);
        }
        return SearchPayoutResult.from(payout);
    }

    @Override
    @Transactional(readOnly = true)
    public Slice<SearchPayoutResult> searchPayouts(PayoutSearchQuery query, Pageable pageable) {
        Slice<Payout> payouts = payoutRepository.searchPayouts(query, pageable);
        return payouts.map(SearchPayoutResult::from);
    }

    @Override
    @Transactional(readOnly = true)
    public Slice<SearchPayoutTxResult> searchPayoutTxs(PayoutTxSearchQuery query, Pageable pageable) {
        Slice<PayoutTx> txs = payoutRepository.searchPayoutTxs(query, pageable);
        return txs.map(SearchPayoutTxResult::from);
    }

}

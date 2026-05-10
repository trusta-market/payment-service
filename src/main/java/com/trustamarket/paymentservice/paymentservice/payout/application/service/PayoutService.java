package com.trustamarket.paymentservice.paymentservice.payout.application.service;

import com.trustamarket.paymentservice.paymentservice.payout.application.dto.command.CreatePayoutCommand;
import com.trustamarket.paymentservice.paymentservice.payout.application.dto.result.CreatePayoutResult;
import com.trustamarket.paymentservice.paymentservice.payout.application.event.PayoutRequestedEvent;
import com.trustamarket.paymentservice.paymentservice.payout.application.port.in.PayoutUseCase;
import com.trustamarket.paymentservice.paymentservice.payout.domain.entity.Payout;
import com.trustamarket.paymentservice.paymentservice.payout.domain.exception.PayoutErrorCode;
import com.trustamarket.paymentservice.paymentservice.payout.domain.exception.PayoutException;
import com.trustamarket.paymentservice.paymentservice.payout.domain.repository.PayoutRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
            // TODO: PayoutErrorCode.DUPLICATE_PAYOUT_REQUEST 추가 필요
        }
    }
}

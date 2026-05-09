package com.trustamarket.paymentservice.paymentservice.payout.application.service;

import com.trustamarket.paymentservice.paymentservice.payment.domain.vo.Amount;
import com.trustamarket.paymentservice.paymentservice.payout.application.dto.command.CreatePayoutCommand;
import com.trustamarket.paymentservice.paymentservice.payout.application.dto.result.CreatePayoutResult;
import com.trustamarket.paymentservice.paymentservice.payout.application.port.PayoutUseCase;
import com.trustamarket.paymentservice.paymentservice.payout.domain.entity.Payout;
import com.trustamarket.paymentservice.paymentservice.payout.domain.exception.PayoutErrorCode;
import com.trustamarket.paymentservice.paymentservice.payout.domain.exception.PayoutException;
import com.trustamarket.paymentservice.paymentservice.payout.domain.repository.PayoutRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PayoutService implements PayoutUseCase {

    private final PayoutRepository payoutRepository;

    @Override
    @Transactional
    public CreatePayoutResult createPayout(CreatePayoutCommand command) {
        try {
            Payout payout = Payout.create(
                    command.userId(),
                    command.pointTxRequestHistoryId(),
                    Amount.of(command.withdrawAmount())
            );
            Payout saved = payoutRepository.saveAndFlush(payout);

            return CreatePayoutResult.from(saved);
        } catch (DataIntegrityViolationException e) {
            throw new PayoutException(PayoutErrorCode.DUPLICATE_PAYOUT_REQUEST);
            // TODO: PayoutErrorCode.DUPLICATE_PAYOUT_REQUEST 추가 필요
        }
    }
}

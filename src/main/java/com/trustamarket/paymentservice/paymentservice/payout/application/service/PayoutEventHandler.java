package com.trustamarket.paymentservice.paymentservice.payout.application.service;

import com.trustamarket.paymentservice.paymentservice.payout.application.event.PayoutRequestedEvent;
import com.trustamarket.paymentservice.paymentservice.payout.application.port.out.pgClient.PgClientPort;
import com.trustamarket.paymentservice.paymentservice.payout.application.port.out.pgClient.PgPayoutResult;
import com.trustamarket.paymentservice.paymentservice.payout.application.port.out.user.UserAccount;
import com.trustamarket.paymentservice.paymentservice.payout.application.port.out.user.UserAccountPort;
import com.trustamarket.paymentservice.paymentservice.payout.application.port.out.wallet.PayoutCompletedResult;
import com.trustamarket.paymentservice.paymentservice.payout.application.port.out.wallet.WalletPort;
import com.trustamarket.paymentservice.paymentservice.payout.domain.entity.Payout;
import com.trustamarket.paymentservice.paymentservice.payout.domain.enums.PayoutStatus;
import com.trustamarket.paymentservice.paymentservice.payout.domain.repository.PayoutRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class PayoutEventHandler {

    private final PayoutRepository payoutRepository;
    private final UserAccountPort userAccountPort;
    private final PgClientPort pgClientPort;
    private final WalletPort walletPort;

    @Async
    @TransactionalEventListener
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void handle(PayoutRequestedEvent event) {
        Payout payout = payoutRepository.findById(event.payoutId());

        try {
            UserAccount account = userAccountPort.getUserAccount(payout.getUserId());
            PgPayoutResult pgResult = pgClientPort.requestPayout(payout, account);

            if (pgResult.status() == PayoutStatus.SUCCESS) {
                payout.complete();
            } else {
                payout.fail(pgResult.failReason());
            }

            PayoutCompletedResult result = PayoutCompletedResult.from(
                    payout.getUserId(),
                    payout.getPayoutId(),
                    payout.getPointTxRequestHistoryId(),
                    payout.getStatus(),
                    payout.getAmount()
            );
            walletPort.payoutCompleted(result);

        } catch (Exception e) {
            payout.fail(e.getMessage());
            PayoutCompletedResult result = PayoutCompletedResult.from(
                    payout.getUserId(),
                    payout.getPayoutId(),
                    payout.getPointTxRequestHistoryId(),
                    payout.getStatus(),
                    payout.getAmount()
            );
            walletPort.payoutCompleted(result);
        }
    }
}
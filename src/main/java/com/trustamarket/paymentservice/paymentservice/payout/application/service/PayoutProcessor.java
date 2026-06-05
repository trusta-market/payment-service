package com.trustamarket.paymentservice.paymentservice.payout.application.service;

import com.trustamarket.paymentservice.paymentservice.payout.application.port.out.pgClient.PgClientPort;
import com.trustamarket.paymentservice.paymentservice.payout.application.port.out.pgClient.PgPayoutRequest;
import com.trustamarket.paymentservice.paymentservice.payout.application.port.out.pgClient.PgPayoutResult;
import com.trustamarket.paymentservice.paymentservice.payout.application.port.out.user.UserAccount;
import com.trustamarket.paymentservice.paymentservice.payout.application.port.out.user.UserAccountPort;
import com.trustamarket.paymentservice.paymentservice.payout.application.port.out.wallet.PayoutCompletedResult;
import com.trustamarket.paymentservice.paymentservice.payout.application.port.out.wallet.WalletPort;
import com.trustamarket.paymentservice.paymentservice.payout.domain.entity.Payout;
import com.trustamarket.paymentservice.paymentservice.payout.domain.enums.PayoutStatus;
import com.trustamarket.paymentservice.paymentservice.payout.domain.repository.PayoutRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class PayoutProcessor {

    private final PayoutRepository payoutRepository;
    private final PayoutService payoutService;
    private final UserAccountPort userAccountPort;
    private final PgClientPort pgClientPort;
    private final WalletPort walletPort;

    public void process(UUID payoutId) {
        Payout payout = payoutRepository.findById(payoutId);

        if (payout.getStatus() != PayoutStatus.PROCESSING) {
            log.warn("[Payout] 이미 처리된 요청. payoutId={}, status={}", payout.getPayoutId(), payout.getStatus());
            return;
        }

        try {
            UserAccount account = userAccountPort.getUserAccount(payout.getUserId());
            if (!account.isVerified()) {
                payout = payoutService.fail(payout.getPayoutId(), "출금 계좌 인증이 완료되지 않았습니다.");
                notifyWalletSafely(payout);
                return;
            }

            PgPayoutRequest request = PgPayoutRequest.of(payout, account);
            PgPayoutResult pgResult = pgClientPort.requestPayout(request);

            if (pgResult.status() == PayoutStatus.SUCCESS) {
                payout = payoutService.success(payout.getPayoutId());
            } else {
                payout = payoutService.fail(payout.getPayoutId(), pgResult.failReason());
            }

            notifyWalletSafely(payout);

        } catch (Exception e) {
            log.error("[Payout] 처리 실패. payoutId={}", payoutId, e);
            payout = payoutService.fail(payout.getPayoutId(), e.getMessage());
            notifyWalletSafely(payout);
        }
    }

    private void notifyWalletSafely(Payout payout) {
        try {
            walletPort.payoutCompleted(PayoutCompletedResult.from(payout));
        } catch (Exception e) {
            log.error("[Wallet] 출금 결과 알림 실패. payoutId={}, status={}", payout.getPayoutId(), payout.getStatus(), e);
        }
    }
}

package com.trustamarket.paymentservice.paymentservice.payout.infrastructure.pgMock;

import com.trustamarket.paymentservice.paymentservice.payout.application.port.out.pgClient.PgClientPort;
import com.trustamarket.paymentservice.paymentservice.payout.application.port.out.pgClient.PgPayoutResult;
import com.trustamarket.paymentservice.paymentservice.payout.application.port.out.user.UserAccount;
import com.trustamarket.paymentservice.paymentservice.payout.domain.enums.PayoutStatus;
import org.springframework.stereotype.Component;

@Component
public class PgMockClient implements PgClientPort {

    @Override
    public PgPayoutResult requestPayout(PgPayoutRequest request, UserAccount account) {

        // 처리 시간 시뮬레이션 3초
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        //todo: 응답 금액 검증 필요

        // 95% 성공
        if (Math.random() > 0.05) {
            return new PgPayoutResult(PayoutStatus.SUCCESS, null);
        } else {
            return new PgPayoutResult(PayoutStatus.FAILED, "INSUFFICIENT_BALANCE");
        }
    }
}

package com.trustamarket.paymentservice.paymentservice.payout.infrastructure.pgMock;

import com.trustamarket.paymentservice.paymentservice.payout.application.port.out.pgClient.PgClientPort;
import com.trustamarket.paymentservice.paymentservice.payout.application.port.out.pgClient.PgPayoutResult;
import com.trustamarket.paymentservice.paymentservice.payout.application.port.out.user.UserAccount;
import com.trustamarket.paymentservice.paymentservice.payout.domain.entity.Payout;
import org.springframework.stereotype.Component;

@Component
public class PgMockClient implements PgClientPort {

    @Override
    public PgPayoutResult requestPayout(Payout payout, UserAccount account) {

        // 처리 시간 시뮬레이션
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // 95% 성공
        if (Math.random() > 0.05) {
            return new PgPayoutResult("success", null);
        } else {
            return new PgPayoutResult("fail", "INSUFFICIENT_BALANCE");
        }
    }
}

package com.trustamarket.paymentservice.paymentservice.payout.infrastructure.pgMock;

import com.trustamarket.paymentservice.paymentservice.payout.application.port.out.pgClient.PgClientPort;
import com.trustamarket.paymentservice.paymentservice.payout.application.port.out.pgClient.PgPayoutRequest;
import com.trustamarket.paymentservice.paymentservice.payout.application.port.out.pgClient.PgPayoutResult;
import com.trustamarket.paymentservice.paymentservice.payout.domain.enums.PayoutStatus;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("mocktest")
public class PgMockClient implements PgClientPort {

    @Override
    public PgPayoutResult requestPayout(PgPayoutRequest request) {

        // 실제 PG사 연결시 sleep 제거, PG사 클라이언트 대체 필수
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

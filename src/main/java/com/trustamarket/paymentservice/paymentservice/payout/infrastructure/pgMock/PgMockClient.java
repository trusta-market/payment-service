package com.trustamarket.paymentservice.paymentservice.payout.infrastructure.pgMock;

import com.trustamarket.paymentservice.paymentservice.payout.application.port.out.pgClient.PgClientPort;
import com.trustamarket.paymentservice.paymentservice.payout.application.port.out.pgClient.PgPayoutRequest;
import com.trustamarket.paymentservice.paymentservice.payout.application.port.out.pgClient.PgPayoutResult;
import com.trustamarket.paymentservice.paymentservice.payout.domain.enums.PayoutStatus;
import com.trustamarket.paymentservice.paymentservice.payout.domain.exception.PayoutErrorCode;
import com.trustamarket.paymentservice.paymentservice.payout.domain.exception.PayoutException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
@Profile("mocktest")
public class PgMockClient implements PgClientPort {

    @Value("${toss.mock.failure-rate}")
    private double failureRate;

    private final Random random = new Random();

    @Override
    public PgPayoutResult requestPayout(PgPayoutRequest request) {

        // 실제 PG사 연결시 sleep 제거, PG사 클라이언트 대체 필수
        // 처리 시간 시뮬레이션 3초
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        if (random.nextDouble() < failureRate) { //PG사 실패 확률
            throw new PayoutException(PayoutErrorCode.PAYOUT_CONFIRM_UNKNOWN);
        }

        //todo: 재시도로직 구축시 실패 확률추가
        return new PgPayoutResult(PayoutStatus.SUCCESS, null);

    }
}

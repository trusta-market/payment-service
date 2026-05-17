package com.trustamarket.paymentservice.paymentservice.payout.infrastructure.pgNoop;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.trustamarket.paymentservice.paymentservice.payout.application.port.out.pgClient.PgClientPort;
import com.trustamarket.paymentservice.paymentservice.payout.application.port.out.pgClient.PgPayoutRequest;
import com.trustamarket.paymentservice.paymentservice.payout.application.port.out.pgClient.PgPayoutResult;

// K8s 프로필 임시 NoOp. PayoutEventHandler DI 만족용 — payout 엔드포인트 호출 시점에 명시적 fail.
// 실 PG 클라이언트 도입 시 제거.
@Component
@Profile("k8s & !mocktest")
public class PgNoOpClient implements PgClientPort {

	@Override
	public PgPayoutResult requestPayout(PgPayoutRequest request) {
		throw new UnsupportedOperationException(
			"payout not wired in k8s profile — PG client implementation pending");
	}
}

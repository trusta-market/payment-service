package com.trustamarket.paymentservice.paymentservice.domain.vo;

public record Amount(long value) {
	public Amount {
		if (value <= 0) throw new IllegalArgumentException("결제 금액은 0보다 커야 합니다.");
	}

	public static Amount of(long value) {
		return new Amount(value);
	}
}

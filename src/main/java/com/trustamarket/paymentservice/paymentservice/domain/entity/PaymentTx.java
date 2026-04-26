package com.trustamarket.paymentservice.paymentservice.domain.entity;

import com.trustamarket.paymentservice.paymentservice.domain.enums.PaymentTxType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Getter
@Entity
@Table(name = "p_payment_transactions")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PaymentTx {

	@Id
	@Column(name = "payment_tx_id", nullable = false, updatable = false)
	private UUID paymentTxId;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "payment_id", nullable = false)
	private Payment payment;

	@Enumerated(EnumType.STRING)
	@Column(name = "tx_type", nullable = false, length = 30, updatable = false)
	private PaymentTxType txType;

	@Column(name = "amount", nullable = false)
	private long amount;

	@Column(name="payment_key", length = 200)
	private String paymentKey;

	@Column(name = "pg_response_code", length = 50)
	private String pgResponseCode;

	@Column(name = "pg_response_message", length = 255)
	private String pgResponseMessage;

	@Column(name = "created_at", nullable = false, updatable = false)
	private Instant createdAt;

	public static PaymentTx createRequest(
			long amount
	) {
		PaymentTx tx = new PaymentTx();

		tx.paymentTxId = UUID.randomUUID();
		tx.txType = PaymentTxType.REQUESTED;
		tx.amount = amount;
		tx.createdAt = Instant.now();
        return tx;
    }

	public static PaymentTx createSuccess(
			long amount,
			String paymentKey

	) {
		PaymentTx tx = new PaymentTx();

		tx.paymentTxId = UUID.randomUUID();
		tx.txType = PaymentTxType.SUCCESS;
		tx.amount = amount;
		tx.paymentKey = paymentKey;
		tx.createdAt = Instant.now();
		return tx;
	}

	public static PaymentTx createFail(
			long amount,
			String pgResponseCode,
			String pgResponseMessage
	) {
		PaymentTx tx = new PaymentTx();

		tx.paymentTxId = UUID.randomUUID();
		tx.txType = PaymentTxType.FAILED;
		tx.amount = amount;
		tx.pgResponseCode = pgResponseCode;
		tx.pgResponseMessage = pgResponseMessage;
		tx.createdAt = Instant.now();
		return tx;
	}

	void assignPayment(Payment payment) {
		this.payment = payment;
	}
}
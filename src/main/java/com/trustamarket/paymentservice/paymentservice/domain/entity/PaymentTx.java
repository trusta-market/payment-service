package com.trustamarket.paymentservice.paymentservice.domain.entity;

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
import org.hibernate.annotations.DynamicUpdate;

import java.time.OffsetDateTime;
import java.util.UUID;

import com.trustamarket.paymentservice.paymentservice.domain.enums.PaymentTxType;
import com.trustamarket.paymentservice.paymentservice.domain.vo.Amount;

@Getter
@Entity
@DynamicUpdate
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
	@Column(name = "tx_type", nullable = false, length = 30)
	private PaymentTxType txType;

	@Column(name = "amount", nullable = false)
	private long amount;

	@Column(name = "pg_tx_id", length = 100)
	private String pgTxId;

	@Column(name = "pg_response_code", length = 50)
	private String pgResponseCode;

	@Column(name = "pg_response_message", length = 255)
	private String pgResponseMessage;

	@Column(name = "created_at", nullable = false, updatable = false)
	private OffsetDateTime createdAt;

	@Column(name = "created_by")
	private UUID createdBy;

	@Column(name = "updated_at")
	private OffsetDateTime updatedAt;

	@Column(name = "updated_by")
	private UUID updatedBy;

	public PaymentTx(
		UUID paymentTxId,
		PaymentTxType txType,
		Amount amount,
		UUID createdBy
	) {
		this.paymentTxId = paymentTxId;
		this.txType = txType;
		this.amount = amount.value();
		this.createdAt = OffsetDateTime.now();
		this.createdBy = createdBy;
	}

	public void assignPayment(Payment payment) {
		this.payment = payment;
	}

	private void updateInfo(UUID updatedBy) {
		this.updatedAt = OffsetDateTime.now();
		this.updatedBy = updatedBy;
	}
}
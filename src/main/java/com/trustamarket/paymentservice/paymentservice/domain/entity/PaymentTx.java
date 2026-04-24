package com.trustamarket.paymentservice.paymentservice.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import java.time.OffsetDateTime;
import java.util.UUID;

import com.trustamarket.paymentservice.paymentservice.domain.enums.PaymentTxStatus;
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

	@Enumerated(EnumType.STRING)
	@Column(name = "status", nullable = false, length = 30)
	private PaymentTxStatus status;

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
		this.status = PaymentTxStatus.REQUESTED;
		this.amount = amount.value();
		this.createdAt = OffsetDateTime.now();
		this.createdBy = createdBy;
	}

	public void success(String pgTxId, String pgResponseCode, String pgResponseMessage, UUID updatedBy) {
		if(this.status != PaymentTxStatus.REQUESTED){
			throw new IllegalStateException("결제 상태 전이 오류");
		}
		this.status = PaymentTxStatus.SUCCESS;
		this.pgTxId = pgTxId;
		this.pgResponseCode = pgResponseCode;
		this.pgResponseMessage = pgResponseMessage;
		updateInfo(updatedBy);
	}

	public void fail(String pgTxId, String pgResponseCode, String pgResponseMessage, UUID updatedBy) {
		if(this.status != PaymentTxStatus.REQUESTED){
			throw new IllegalStateException("결제 상태 전이 오류");
		}
		this.status = PaymentTxStatus.FAILED;
		this.pgTxId = pgTxId;
		this.pgResponseCode = pgResponseCode;
		this.pgResponseMessage = pgResponseMessage;
		updateInfo(updatedBy);
	}

	public void assignPayment(Payment payment) {
		this.payment = payment;
	}

	private void updateInfo(UUID updatedBy) {
		this.updatedAt = OffsetDateTime.now();
		this.updatedBy = updatedBy;
	}
}
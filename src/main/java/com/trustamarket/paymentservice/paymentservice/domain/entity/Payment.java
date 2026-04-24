package com.trustamarket.paymentservice.paymentservice.domain.entity;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.trustamarket.paymentservice.paymentservice.domain.enums.PaymentStatus;
import com.trustamarket.paymentservice.paymentservice.domain.vo.Amount;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "p_payments")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Payment {

	@Id
	@Column(name = "payment_id", nullable = false, updatable = false)
	private UUID paymentId;

	@Column(name = "order_id", nullable = false, updatable = false)
	private UUID orderId;

	@Column(name = "buyer_id", nullable = false, updatable = false)
	private UUID buyerId;

	@Column(name="payment_key", length = 200, updatable = false)
	private String paymentKey;

	@Enumerated(EnumType.STRING)
	@Column(name = "payment_status", nullable = false, length = 30)
	private PaymentStatus paymentStatus;

	@Column(name = "amount", nullable = false)
	private long amount;

	@Version
	@Column(name = "version", nullable = false)
	private Integer version;

	@Column(name = "created_at", nullable = false, updatable = false)
	private OffsetDateTime createdAt;

	@Column(name = "created_by")
	private UUID createdBy;

	@Column(name = "updated_at")
	private OffsetDateTime updatedAt;

	@Column(name = "updated_by")
	private UUID updatedBy;

	@OneToMany(mappedBy = "payment", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<PaymentTx> transactions = new ArrayList<>();

	public Payment(
		UUID paymentId,
		UUID orderId,
		UUID buyerId,
		Amount amount,
		UUID createdBy
	) {
		this.paymentId = paymentId;
		this.orderId = orderId;
		this.buyerId = buyerId;
		this.paymentStatus = PaymentStatus.REQUESTED;
		this.amount = amount.value();
		this.createdAt = OffsetDateTime.now();
		this.createdBy = createdBy;
	}

	public void successPayment(UUID updatedBy, String paymentKey) {
		this.paymentStatus = PaymentStatus.SUCCESS;
		this.paymentKey = paymentKey;
		updateInfo(updatedBy);
	}

	public void failPayment(UUID updatedBy) {
		this.paymentStatus = PaymentStatus.FAILED;
		updateInfo(updatedBy);
	}

	public void addTransaction(PaymentTx transaction) {
		this.transactions.add(transaction);
	}

	private void updateInfo(UUID updatedBy) {
		this.updatedAt = OffsetDateTime.now();
		this.updatedBy = updatedBy;
	}
}
package com.trustamarket.paymentservice.paymentservice.domain.entity;

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

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Entity
@Table(name = "p_payments")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Payment {

	@Id
	@Column(name = "payment_id", nullable = false, updatable = false)
	private UUID paymentId;

	@Column(name="payment_key", length = 200)
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
	private Instant createdAt;

	@Column(name = "created_by")
	private UUID createdBy;

	@Column(name = "updated_at")
	private Instant updatedAt;

	@OneToMany(mappedBy = "payment", cascade = CascadeType.PERSIST)
	private List<PaymentTx> transactions = new ArrayList<>();

	public static Payment create(
			Amount amount
	) {
		Payment payment = new Payment();

		payment.paymentId = UUID.randomUUID();
		payment.paymentStatus = PaymentStatus.REQUESTED;
		payment.amount = amount.value();
		payment.createdAt = Instant.now();

		payment.addTransaction(PaymentTx.createRequest(amount));
		return payment;
	}

	public void successPayment(String paymentKey, long approvedAmount) {
		if(this.paymentStatus != PaymentStatus.REQUESTED){
			throw new IllegalStateException("결제 상태 전이 오류");
		}
		if (approvedAmount != this.amount) {
			throw new IllegalStateException("PG 승인 금액 불일치");
		}

		this.paymentStatus = PaymentStatus.SUCCESS;
		this.paymentKey = paymentKey;
		this.updatedAt = Instant.now();

		this.addTransaction(PaymentTx.createSuccess(Amount.of(approvedAmount), paymentKey));
	}

	public void failPayment(String pgCode,  String pgMessage) {
		if(this.paymentStatus != PaymentStatus.REQUESTED){
			throw new IllegalStateException("결제 상태 전이 오류");
		}
		this.paymentStatus = PaymentStatus.FAILED;
		this.updatedAt = Instant.now();

		this.addTransaction(PaymentTx.createFail(Amount.of(amount), pgCode, pgMessage));
	}

	private void addTransaction(PaymentTx transaction) {
		this.transactions.add(transaction);
		transaction.assignPayment(this);
	}
}
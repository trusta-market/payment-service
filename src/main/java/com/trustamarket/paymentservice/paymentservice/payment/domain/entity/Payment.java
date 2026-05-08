package com.trustamarket.paymentservice.paymentservice.payment.domain.entity;

import com.trustamarket.common.domain.BaseTimeEntity;
import com.trustamarket.paymentservice.paymentservice.payment.domain.enums.PaymentStatus;
import com.trustamarket.paymentservice.paymentservice.payment.domain.exception.PaymentErrorCode;
import com.trustamarket.paymentservice.paymentservice.payment.domain.exception.PaymentException;
import com.trustamarket.paymentservice.paymentservice.payment.domain.vo.Amount;
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

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Entity
@Table(name = "p_payments")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Payment extends BaseTimeEntity {

	@Id
	@Column(name = "payment_id", nullable = false, updatable = false)
	private UUID paymentId;

	@Column(name = "user_id", nullable = false, updatable = false)
	private UUID userId;

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

	@OneToMany(mappedBy = "payment", cascade = CascadeType.PERSIST)
	private List<PaymentTx> transactions = new ArrayList<>();

	public static Payment create(
			UUID userId,
			UUID paymentId,
			Amount amount
	) {
		Payment payment = new Payment();

		payment.userId = userId;
		payment.paymentId = paymentId;
		payment.paymentStatus = PaymentStatus.REQUESTED;
		payment.amount = amount.value();

		payment.addTransaction(PaymentTx.createRequest(userId, amount));
		return payment;
	}

	public void validateConfirm(String paymentKey, long confirmAmount){
		if (this.paymentStatus != PaymentStatus.REQUESTED) {
			throw new PaymentException(PaymentErrorCode.INVALID_PAYMENT_STATUS);
		}
		if(paymentKey == null || paymentKey.isBlank()){
			throw new PaymentException(PaymentErrorCode.INVALID_PAYMENT_KEY);
		}
		if (this.amount != confirmAmount) {
			throw new PaymentException(PaymentErrorCode.PAYMENT_AMOUNT_MISMATCH);
		}
	}

	public void successPayment(String paymentKey, long approvedAmount) {
		if(this.paymentStatus != PaymentStatus.REQUESTED){
			throw new PaymentException(PaymentErrorCode.INVALID_PAYMENT_STATUS);
		}
		if (approvedAmount != this.amount) {
			throw new PaymentException(PaymentErrorCode.PAYMENT_AMOUNT_MISMATCH);
		}

		this.paymentStatus = PaymentStatus.SUCCESS;
		this.paymentKey = paymentKey;

		this.addTransaction(PaymentTx.createSuccess(userId, Amount.of(approvedAmount), paymentKey));
	}

	public void failPayment(String pgCode,  String pgMessage) {
		if(this.paymentStatus != PaymentStatus.REQUESTED){
			throw new PaymentException(PaymentErrorCode.INVALID_PAYMENT_STATUS);
		}
		this.paymentStatus = PaymentStatus.FAILED;

		this.addTransaction(PaymentTx.createFail(userId, Amount.of(amount), pgCode, pgMessage));
	}

	private void addTransaction(PaymentTx transaction) {
		this.transactions.add(transaction);
		transaction.assignPayment(this);
	}
}
package com.trustamarket.paymentservice.paymentservice.infrastructure.repository;

import com.trustamarket.paymentservice.paymentservice.domain.entity.Payment;
import com.trustamarket.paymentservice.paymentservice.domain.enums.PaymentStatus;
import org.springframework.data.jpa.domain.Specification;

import java.util.UUID;

public class PaymentSpecification {
    public static Specification<Payment> hasUserId(UUID userId) {
        return (root, query, cb) ->
                userId != null ? cb.equal(root.get("userId"), userId) : null;
    }

    public static Specification<Payment> hasPaymentId(UUID paymentId) {
        return (root, query, cb) ->
                paymentId != null ? cb.equal(root.get("paymentId"), paymentId) : null;
    }

    public static Specification<Payment> hasStatusEq(PaymentStatus status) {
        return (root, query, cb) ->
                status != null ? cb.equal(root.get("paymentStatus"), status) : null;
    }

    public static Specification<Payment> amountBetween(long min, long max) {
        return (root, query, cb) ->
                (min == 0 && max == Long.MAX_VALUE) ? null : cb.between(root.get("amount"), min, max);
    }
}

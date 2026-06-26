package com.trustamarket.paymentservice.paymentservice.payment.infrastructure.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.trustamarket.paymentservice.paymentservice.payment.application.dto.query.PaymentSearchQuery;
import com.trustamarket.paymentservice.paymentservice.payment.domain.entity.Payment;
import com.trustamarket.paymentservice.paymentservice.payment.domain.entity.QPayment;
import com.trustamarket.paymentservice.paymentservice.payment.domain.exception.PaymentErrorCode;
import com.trustamarket.paymentservice.paymentservice.payment.domain.exception.PaymentException;
import com.trustamarket.paymentservice.paymentservice.payment.domain.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class PaymentRepositoryAdapter implements PaymentRepository {

    private final PaymentJpaRepository paymentJpaRepository;
    private final JPAQueryFactory queryFactory;

    @Override
    public Payment saveAndFlush(Payment payment) {
        return paymentJpaRepository.saveAndFlush(payment);
    }

    @Override
    public Payment findById(UUID paymentId) {
        return paymentJpaRepository.findById(paymentId)
                .orElseThrow(() -> new PaymentException(PaymentErrorCode.PAYMENT_NOT_FOUND));
    }

    @Override
    public Slice<Payment> searchPayments(PaymentSearchQuery query, Pageable pageable) {
        QPayment payment = QPayment.payment;

        BooleanBuilder builder = new BooleanBuilder();
        if (query.userId() != null) {
            builder.and(payment.userId.eq(query.userId()));
        }
        if (query.paymentId() != null) {
            builder.and(payment.paymentId.eq(query.paymentId()));
        }
        if (query.status() != null) {
            builder.and(payment.paymentStatus.eq(query.status()));
        }
        if (query.minAmount() != 0 || query.maxAmount() != Long.MAX_VALUE) {
            builder.and(payment.amount.between(query.minAmount(), query.maxAmount()));
        }

        List<Payment> results = queryFactory
                .selectFrom(payment)
                .where(builder)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1L)
                .fetch();

        boolean hasNext = results.size() > pageable.getPageSize();
        if (hasNext) {
            results.remove(results.size() - 1);
        }

        return new SliceImpl<>(results, pageable, hasNext);
    }
}

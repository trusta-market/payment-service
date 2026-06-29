package com.trustamarket.paymentservice.paymentservice.payout.infrastructure.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.trustamarket.paymentservice.paymentservice.payout.application.dto.query.PayoutSearchQuery;
import com.trustamarket.paymentservice.paymentservice.payout.application.dto.query.PayoutTxSearchQuery;
import com.trustamarket.paymentservice.paymentservice.payout.domain.entity.Payout;
import com.trustamarket.paymentservice.paymentservice.payout.domain.entity.PayoutTx;
import com.trustamarket.paymentservice.paymentservice.payout.domain.entity.QPayout;
import com.trustamarket.paymentservice.paymentservice.payout.domain.entity.QPayoutTx;
import com.trustamarket.paymentservice.paymentservice.payout.domain.exception.PayoutErrorCode;
import com.trustamarket.paymentservice.paymentservice.payout.domain.exception.PayoutException;
import com.trustamarket.paymentservice.paymentservice.payout.domain.repository.PayoutRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class PayoutRepositoryAdapter implements PayoutRepository {

    private final PayoutJpaRepository payoutJpaRepository;
    private final JPAQueryFactory queryFactory;

    @Override
    public Payout saveAndFlush(Payout payout) {
        return payoutJpaRepository.saveAndFlush(payout);
    }

    @Override
    public Payout save(Payout payout) {
        return payoutJpaRepository.save(payout);
    }

    @Override
    public Payout findById(UUID payoutId) {
        Payout payout = payoutJpaRepository.findById(payoutId)
                .orElseThrow(() -> new PayoutException(PayoutErrorCode.PAYOUT_NOT_FOUND));
        return payout;
    }

    @Override
    public boolean existsByPointTxRequestHistoryId(UUID pointTxRequestHistoryId) {
        return payoutJpaRepository.existsByPointTxRequestHistoryId(pointTxRequestHistoryId);
    }

    @Override
    public Slice<Payout> searchPayouts(PayoutSearchQuery query, Pageable pageable) {
        QPayout payout = QPayout.payout;

        BooleanBuilder builder = new BooleanBuilder();
        if (query.payoutId() != null) {
            builder.and(payout.payoutId.eq(query.payoutId()));
        }
        if (query.userId() != null) {
            builder.and(payout.userId.eq(query.userId()));
        }
        if (query.status() != null) {
            builder.and(payout.status.eq(query.status()));
        }
        if (query.minAmount() != 0 || query.maxAmount() != Long.MAX_VALUE) {
            builder.and(payout.amount.between(query.minAmount(), query.maxAmount()));
        }

        List<Payout> results = queryFactory
                .selectFrom(payout)
                .where(builder)
                .orderBy(payout.createdAt.desc(), payout.payoutId.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1L)
                .fetch();

        boolean hasNext = results.size() > pageable.getPageSize();
        if (hasNext) {
            results.remove(results.size() - 1);
        }

        return new SliceImpl<>(results, pageable, hasNext);
    }

    @Override
    public Slice<PayoutTx> searchPayoutTxs(PayoutTxSearchQuery query, Pageable pageable) {
        QPayoutTx payoutTx = QPayoutTx.payoutTx;

        BooleanBuilder builder = new BooleanBuilder();
        if (query.payoutId() != null) {
            builder.and(payoutTx.payout.payoutId.eq(query.payoutId()));
        }
        if (query.userId() != null) {
            builder.and(payoutTx.userId.eq(query.userId()));
        }
        if (query.txType() != null) {
            builder.and(payoutTx.txType.eq(query.txType()));
        }
        if (query.minAmount() != 0 || query.maxAmount() != Long.MAX_VALUE) {
            builder.and(payoutTx.amount.between(query.minAmount(), query.maxAmount()));
        }

        List<PayoutTx> results = queryFactory
                .selectFrom(payoutTx)
                .leftJoin(payoutTx.payout).fetchJoin()
                .where(builder)
                .orderBy(payoutTx.createdAt.desc(), payoutTx.payoutTxId.desc())
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

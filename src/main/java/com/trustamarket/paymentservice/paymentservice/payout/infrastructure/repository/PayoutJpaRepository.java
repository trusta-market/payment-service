package com.trustamarket.paymentservice.paymentservice.payout.infrastructure.repository;

import com.trustamarket.paymentservice.paymentservice.payout.domain.entity.Payout;
import com.trustamarket.paymentservice.paymentservice.payout.domain.enums.PayoutStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

public interface PayoutJpaRepository extends JpaRepository<Payout, UUID> {
    List<Payout> findByStatus(PayoutStatus status, Pageable pageable);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Payout p SET p.status = 'PROCESSING', p.updatedAt = CURRENT_TIMESTAMP WHERE p.payoutId IN ?1 AND p.status = 'REQUESTED'")
    void updateStatusToProcessing(List<UUID> ids);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Payout p SET p.status = 'REQUESTED', p.updatedAt = CURRENT_TIMESTAMP WHERE p.status = 'PROCESSING' AND p.updatedAt < ?1")
    void resetToRequested(Instant threshold);

    List<Payout> findByStatusAndPayoutIdIn(PayoutStatus status, List<UUID> ids);
}

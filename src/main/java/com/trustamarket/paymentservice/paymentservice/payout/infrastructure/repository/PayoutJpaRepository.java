package com.trustamarket.paymentservice.paymentservice.payout.infrastructure.repository;

import com.trustamarket.paymentservice.paymentservice.payout.domain.entity.Payout;
import com.trustamarket.paymentservice.paymentservice.payout.domain.enums.PayoutStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface PayoutJpaRepository extends JpaRepository<Payout, UUID> {
    List<Payout> findByStatus(PayoutStatus status, Pageable pageable);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Payout p SET p.status = 'PROCESSING' WHERE p.payoutId IN :ids AND p.status = 'REQUESTED'")
    void updateStatusToProcessing(@Param("ids") List<UUID> ids);

    List<Payout> findByStatusAndPayoutIdIn(PayoutStatus status, List<UUID> ids);
}

package com.trustamarket.paymentservice.paymentservice.payout.infrastructure.repository;

import com.trustamarket.paymentservice.paymentservice.payout.domain.repository.PayoutRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PayoutRepositoryAdapter implements PayoutRepository {
}

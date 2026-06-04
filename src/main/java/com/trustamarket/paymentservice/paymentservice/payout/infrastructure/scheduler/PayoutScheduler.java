package com.trustamarket.paymentservice.paymentservice.payout.infrastructure.scheduler;

import com.trustamarket.paymentservice.paymentservice.payout.application.service.PayoutProcessor;
import com.trustamarket.paymentservice.paymentservice.payout.domain.entity.Payout;
import com.trustamarket.paymentservice.paymentservice.payout.domain.repository.PayoutRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class PayoutScheduler {

    private static final int BATCH_SIZE = 100;

    private final PayoutRepository payoutRepository;
    private final PayoutProcessor payoutProcessor;

    @Scheduled(fixedDelay = 5000)
    public void process() {
        List<Payout> payouts = payoutRepository.findRequestedPayouts(BATCH_SIZE);
        if (payouts.isEmpty()) {
            return;
        }

        log.info("[PayoutScheduler] 처리 대상 {}건", payouts.size());
        for (Payout payout : payouts) {
            payoutProcessor.process(payout.getPayoutId());
        }
    }
}

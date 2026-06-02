package com.trustamarket.paymentservice.paymentservice.payout.infrastructure.user;

import com.trustamarket.common.response.CommonResponse;
import com.trustamarket.paymentservice.paymentservice.payout.application.port.out.user.UserAccount;
import com.trustamarket.paymentservice.paymentservice.payout.application.port.out.user.UserAccountPort;
import com.trustamarket.paymentservice.paymentservice.payout.infrastructure.user.dto.UserAccountResponse;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserAccountAdapter implements UserAccountPort {

    private final UserFeignClient userFeignClient;

    @Retryable(
            retryFor = FeignException.class,
            maxAttempts = 3,
            backoff = @Backoff(delay = 1000)
    )
    @Override
    public UserAccount getUserAccount(UUID userId) {
        ResponseEntity<CommonResponse<UserAccountResponse>> response = userFeignClient.getUserAccount(userId);
        CommonResponse<UserAccountResponse> body = response.getBody();
        UserAccountResponse data = body != null ? body.data() : null;

        if (data == null) {
            throw new IllegalStateException("유저의 계좌정보를 찾을 수 없습니다.");
        }
        if (!data.userId().equals(userId)) {
            throw new IllegalStateException("유저정보가 일치하지 않습니다.");
        }

        return new UserAccount(
                data.bankCode(),
                data.accountNumber(),
                data.accountHolder(),
                data.isVerified()
        );
    }

    @Recover
    public UserAccount recover(FeignException e, UUID userId) {
        log.error("[User] 재시도 모두 실패. userId={}", userId, e);
        throw new IllegalStateException("유저 정보 조회 실패");
    }

    // FeignException이 아닌 예외(ISE, NPE 등)가 발생한 경우 그대로 전파
    @Recover
    public UserAccount recover(Exception e, UUID userId) {
        if (e instanceof RuntimeException re) throw re;
        throw new IllegalStateException(e.getMessage(), e);
    }
}
package com.trustamarket.paymentservice.paymentservice.payout.infrastructure.user;

import com.trustamarket.common.response.CommonResponse;
import com.trustamarket.paymentservice.paymentservice.payout.infrastructure.user.dto.UserAccountResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name="user-service")
public interface UserFeignClient {

    @GetMapping("internal/v1/accounts/verified-default/{userId}")
    ResponseEntity<CommonResponse<UserAccountResponse>> getUserAccount(@PathVariable UUID userId);

}

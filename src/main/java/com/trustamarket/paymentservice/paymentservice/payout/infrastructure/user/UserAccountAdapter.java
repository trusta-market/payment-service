package com.trustamarket.paymentservice.paymentservice.payout.infrastructure.user;

import com.trustamarket.common.response.CommonResponse;
import com.trustamarket.paymentservice.paymentservice.payout.application.port.out.user.UserAccount;
import com.trustamarket.paymentservice.paymentservice.payout.application.port.out.user.UserAccountPort;
import com.trustamarket.paymentservice.paymentservice.payout.infrastructure.user.dto.UserAccountResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UserAccountAdapter implements UserAccountPort {

    private final UserFeignClient userFeignClient;

    @Override
    public UserAccount getUserAccount(UUID userId) {
        CommonResponse<UserAccountResponse> response = userFeignClient.getUserAccount(userId);
        UserAccountResponse data = response.data();

        return new UserAccount(
                data.bankCode(),
                data.accountNumber(),
                data.accountHolder(),
                data.isVerified()
        );
    }

}

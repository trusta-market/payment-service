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

        if(!data.userId().equals(userId)) {
            throw new IllegalStateException("유저정보가 일치하지 않습니다.");
        }
        if(data == null){
            //todo : paymentException으로 수정하기
            throw new IllegalStateException("유저의 계좌정보를 찾을 수 없습니다.");
        }

        return new UserAccount(
                data.bankCode(),
                data.accountNumber(),
                data.accountHolder(),
                data.isVerified()
        );
    }

}

package com.trustamarket.paymentservice.paymentservice.application.port;

import com.trustamarket.paymentservice.paymentservice.application.dto.command.CreatePaymentCommand;
import com.trustamarket.paymentservice.paymentservice.application.dto.command.FailPaymentCommand;
import com.trustamarket.paymentservice.paymentservice.application.dto.command.PaymentDetailCommand;
import com.trustamarket.paymentservice.paymentservice.domain.repository.condition.PaymentSearchCondition;
import com.trustamarket.paymentservice.paymentservice.application.dto.command.SucceededPaymentCommand;
import com.trustamarket.paymentservice.paymentservice.application.dto.result.CreatePaymentResult;
import com.trustamarket.paymentservice.paymentservice.application.dto.result.FailPaymentResult;
import com.trustamarket.paymentservice.paymentservice.application.dto.result.PaymentDetailResult;
import com.trustamarket.paymentservice.paymentservice.application.dto.result.PaymentInfoResult;
import com.trustamarket.paymentservice.paymentservice.application.dto.result.SearchPaymentResult;
import com.trustamarket.paymentservice.paymentservice.application.dto.result.SucceededPaymentResult;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.UUID;

public interface PaymentUseCase {
    CreatePaymentResult createPayment(CreatePaymentCommand command);
    SucceededPaymentResult succeededPayment(SucceededPaymentCommand command);
    FailPaymentResult failPayment(FailPaymentCommand command);

    //조회
    PaymentDetailResult getPaymentDetail(PaymentDetailCommand query);
    Slice<SearchPaymentResult> searchPayments(PaymentSearchCondition query, Pageable pageable);

    //프론트 결제정보 전달 목적
    PaymentInfoResult getPaymentInfo(UUID paymentId);
}
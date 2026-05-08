package com.trustamarket.paymentservice.paymentservice.payment.application.port;

import com.trustamarket.paymentservice.paymentservice.payment.application.dto.command.CreatePaymentCommand;
import com.trustamarket.paymentservice.paymentservice.payment.application.dto.command.FailPaymentCommand;
import com.trustamarket.paymentservice.paymentservice.payment.application.dto.query.PaymentDetailQuery;
import com.trustamarket.paymentservice.paymentservice.payment.application.dto.query.PaymentSearchQuery;
import com.trustamarket.paymentservice.paymentservice.payment.application.dto.command.SucceededPaymentCommand;
import com.trustamarket.paymentservice.paymentservice.payment.application.dto.result.CreatePaymentResult;
import com.trustamarket.paymentservice.paymentservice.payment.application.dto.result.FailPaymentResult;
import com.trustamarket.paymentservice.paymentservice.payment.application.dto.result.PaymentDetailResult;
import com.trustamarket.paymentservice.paymentservice.payment.application.dto.result.PaymentInfoResult;
import com.trustamarket.paymentservice.paymentservice.payment.application.dto.result.SearchPaymentResult;
import com.trustamarket.paymentservice.paymentservice.payment.application.dto.result.SucceededPaymentResult;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.UUID;

public interface PaymentUseCase {
    CreatePaymentResult createPayment(CreatePaymentCommand command);
    SucceededPaymentResult succeededPayment(SucceededPaymentCommand command);
    FailPaymentResult failPayment(FailPaymentCommand command);

    //조회
    PaymentDetailResult getPaymentDetail(PaymentDetailQuery query);
    Slice<SearchPaymentResult> searchPayments(PaymentSearchQuery query, Pageable pageable);

    //프론트 결제정보 전달 목적
    PaymentInfoResult getPaymentInfo(UUID paymentId);
}
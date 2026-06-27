package com.trustamarket.paymentservice.paymentservice.payment.application.port;

import com.trustamarket.paymentservice.paymentservice.payment.application.dto.command.CreatePaymentCommand;
import com.trustamarket.paymentservice.paymentservice.payment.application.dto.command.FailPaymentCommand;
import com.trustamarket.paymentservice.paymentservice.payment.application.dto.command.SucceededPaymentCommand;
import com.trustamarket.paymentservice.paymentservice.payment.application.dto.query.PaymentDetailQuery;
import com.trustamarket.paymentservice.paymentservice.payment.application.dto.query.PaymentSearchQuery;
import com.trustamarket.paymentservice.paymentservice.payment.application.dto.query.PaymentTxSearchQuery;
import com.trustamarket.paymentservice.paymentservice.payment.application.dto.result.CreatePaymentResult;
import com.trustamarket.paymentservice.paymentservice.payment.application.dto.result.FailPaymentResult;
import com.trustamarket.paymentservice.paymentservice.payment.application.dto.result.PaymentDetailResult;
import com.trustamarket.paymentservice.paymentservice.payment.application.dto.result.PaymentInfoResult;
import com.trustamarket.paymentservice.paymentservice.payment.application.dto.result.SearchPaymentResult;
import com.trustamarket.paymentservice.paymentservice.payment.application.dto.result.SearchPaymentTxResult;
import com.trustamarket.paymentservice.paymentservice.payment.domain.entity.Payment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.UUID;

public interface PaymentUseCase {
    CreatePaymentResult createPayment(CreatePaymentCommand command);
    FailPaymentResult failPayment(FailPaymentCommand command);

    //조회
    PaymentDetailResult getPaymentDetail(PaymentDetailQuery query);
    Slice<SearchPaymentResult> searchPayments(PaymentSearchQuery query, Pageable pageable);
    Slice<SearchPaymentTxResult> searchPaymentTxs(PaymentTxSearchQuery query, Pageable pageable);

    //프론트 결제정보 전달 목적
    PaymentInfoResult getPaymentInfo(UUID paymentId);

    // Facade에서 호출할 내부용
    Payment markSuccess(SucceededPaymentCommand command);
    Payment markFail(FailPaymentCommand command);
}

package com.trustamarket.paymentservice.paymentservice;

import com.trustamarket.paymentservice.paymentservice.application.dto.command.CreatePaymentCommand;
import com.trustamarket.paymentservice.paymentservice.application.dto.command.FailPaymentCommand;
import com.trustamarket.paymentservice.paymentservice.application.dto.command.SucceededPaymentCommand;
import com.trustamarket.paymentservice.paymentservice.application.dto.result.CreatePaymentResult;
import com.trustamarket.paymentservice.paymentservice.application.dto.result.FailPaymentResult;
import com.trustamarket.paymentservice.paymentservice.application.dto.result.SucceededPaymentResult;
import com.trustamarket.paymentservice.paymentservice.application.service.PaymentService;
import com.trustamarket.paymentservice.paymentservice.domain.entity.Payment;
import com.trustamarket.paymentservice.paymentservice.domain.enums.PaymentStatus;
import com.trustamarket.paymentservice.paymentservice.domain.exception.PaymentErrorCode;
import com.trustamarket.paymentservice.paymentservice.domain.exception.PaymentException;
import com.trustamarket.paymentservice.paymentservice.domain.repository.PaymentRepository;
import com.trustamarket.paymentservice.paymentservice.domain.vo.Amount;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {

    @Mock
    private PaymentRepository paymentRepository;

    @InjectMocks
    private PaymentService paymentService;

    @Nested
    @DisplayName("결제 생성")
    class CreatePayment {

        @Test
        @DisplayName("정상 - 결제 생성 성공")
        void createPayment_success() {
            // given
            UUID chargeId = UUID.randomUUID();
            long amount = 10000L;
            CreatePaymentCommand command = new CreatePaymentCommand(chargeId, amount);

            Payment payment = Payment.create(chargeId, Amount.of(amount));
            given(paymentRepository.existsByChargeId(chargeId)).willReturn(false);
            given(paymentRepository.save(any(Payment.class))).willReturn(payment);

            // when
            CreatePaymentResult result = paymentService.createPayment(command);

            // then
            assertThat(result.chargeId()).isEqualTo(chargeId);
            assertThat(result.amount()).isEqualTo(amount);
            assertThat(result.paymentStatus()).isEqualTo(PaymentStatus.REQUESTED);
            verify(paymentRepository).save(any(Payment.class));
        }

        @Test
        @DisplayName("실패 - 중복 chargeId로 결제 생성 시 PaymentException(DUPLICATE_CHARGE_ID)")
        void createPayment_duplicateChargeId() {
            // given
            UUID chargeId = UUID.randomUUID();
            CreatePaymentCommand command = new CreatePaymentCommand(chargeId, 10000L);
            given(paymentRepository.existsByChargeId(chargeId)).willReturn(true);

            // when & then
            assertThatThrownBy(() -> paymentService.createPayment(command))
                    .isInstanceOf(PaymentException.class);
        }

        @Test
        @DisplayName("예외 - 금액이 0 이하일 때 IllegalArgumentException")
        void createPayment_invalidAmount() {
            // given
            UUID chargeId = UUID.randomUUID();
            CreatePaymentCommand command = new CreatePaymentCommand(chargeId, 0L);
            given(paymentRepository.existsByChargeId(chargeId)).willReturn(false);

            // when & then
            assertThatThrownBy(() -> paymentService.createPayment(command))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("결제 금액은 0보다 커야 합니다.");
        }

        @Test
        @DisplayName("예외 - DB 저장 실패 시 예외 전파")
        void createPayment_dbSaveFail() {
            // given
            UUID chargeId = UUID.randomUUID();
            CreatePaymentCommand command = new CreatePaymentCommand(chargeId, 10000L);
            given(paymentRepository.existsByChargeId(chargeId)).willReturn(false);
            given(paymentRepository.save(any(Payment.class))).willThrow(new RuntimeException("DB 저장 실패"));

            // when & then
            assertThatThrownBy(() -> paymentService.createPayment(command))
                    .isInstanceOf(RuntimeException.class)
                    .hasMessage("DB 저장 실패");
        }
    }

    @Nested
    @DisplayName("결제 성공 처리")
    class SucceededPayment {

        @Test
        @DisplayName("정상 - 결제 성공 처리")
        void succeededPayment_success() {
            // given
            UUID chargeId = UUID.randomUUID();
            long amount = 10000L;
            Payment payment = Payment.create(chargeId, Amount.of(amount));
            UUID paymentId = payment.getPaymentId();

            SucceededPaymentCommand command = new SucceededPaymentCommand(paymentId, "test_key_123", amount);
            given(paymentRepository.findById(paymentId)).willReturn(payment);

            // when
            SucceededPaymentResult result = paymentService.succeededPayment(command);

            // then
            assertThat(result.paymentStatus()).isEqualTo(PaymentStatus.SUCCESS);
            assertThat(result.amount()).isEqualTo(amount);
        }

        @Test
        @DisplayName("예외 - 존재하지 않는 paymentId로 PaymentException(PAYMENT_NOT_FOUND)")
        void succeededPayment_notFound() {
            // given
            UUID paymentId = UUID.randomUUID();
            SucceededPaymentCommand command = new SucceededPaymentCommand(paymentId, "test_key_123", 10000L);
            given(paymentRepository.findById(paymentId))
                    .willThrow(new PaymentException(PaymentErrorCode.PAYMENT_NOT_FOUND));

            // when & then
            assertThatThrownBy(() -> paymentService.succeededPayment(command))
                    .isInstanceOf(PaymentException.class);
        }

        @Test
        @DisplayName("예외 - PG 승인 금액 불일치 시 PaymentException(PAYMENT_AMOUNT_MISMATCH)")
        void succeededPayment_amountMismatch() {
            // given
            UUID chargeId = UUID.randomUUID();
            Payment payment = Payment.create(chargeId, Amount.of(10000L));
            UUID paymentId = payment.getPaymentId();

            SucceededPaymentCommand command = new SucceededPaymentCommand(paymentId, "test_key_123", 9000L);
            given(paymentRepository.findById(paymentId)).willReturn(payment);

            // when & then
            assertThatThrownBy(() -> paymentService.succeededPayment(command))
                    .isInstanceOf(PaymentException.class);
        }
    }

    @Nested
    @DisplayName("결제 실패 처리")
    class FailPayment {

        @Test
        @DisplayName("정상 - 결제 실패 처리")
        void failPayment_success() {
            // given
            UUID chargeId = UUID.randomUUID();
            Payment payment = Payment.create(chargeId, Amount.of(10000L));
            UUID paymentId = payment.getPaymentId();

            FailPaymentCommand command = new FailPaymentCommand(paymentId, "CANCEL", "사용자 취소");
            given(paymentRepository.findById(paymentId)).willReturn(payment);

            // when
            FailPaymentResult result = paymentService.failPayment(command);

            // then
            assertThat(result.paymentStatus()).isEqualTo(PaymentStatus.FAILED);
        }

        @Test
        @DisplayName("예외 - 존재하지 않는 paymentId로 PaymentException(PAYMENT_NOT_FOUND)")
        void failPayment_notFound() {
            // given
            UUID paymentId = UUID.randomUUID();
            FailPaymentCommand command = new FailPaymentCommand(paymentId, "CANCEL", "사용자 취소");
            given(paymentRepository.findById(paymentId))
                    .willThrow(new PaymentException(PaymentErrorCode.PAYMENT_NOT_FOUND));

            // when & then
            assertThatThrownBy(() -> paymentService.failPayment(command))
                    .isInstanceOf(PaymentException.class);
        }

        @Test
        @DisplayName("예외 - REQUESTED 상태가 아닌 결제 실패 처리 시 PaymentException(INVALID_PAYMENT_STATUS)")
        void failPayment_invalidStatus() {
            // given
            UUID chargeId = UUID.randomUUID();
            Payment payment = Payment.create(chargeId, Amount.of(10000L));
            payment.successPayment("test_key", 10000L); // 이미 SUCCESS 상태
            UUID paymentId = payment.getPaymentId();

            FailPaymentCommand command = new FailPaymentCommand(paymentId, "CANCEL", "사용자 취소");
            given(paymentRepository.findById(paymentId)).willReturn(payment);

            // when & then
            assertThatThrownBy(() -> paymentService.failPayment(command))
                    .isInstanceOf(PaymentException.class);
        }
    }
}
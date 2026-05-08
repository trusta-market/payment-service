package com.trustamarket.paymentservice.paymentservice.payment.presentation;

import com.trustamarket.paymentservice.paymentservice.payment.application.dto.result.PaymentInfoResult;
import com.trustamarket.paymentservice.paymentservice.payment.application.port.PaymentUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.UUID;

@Controller
@RequestMapping("/demo/v1/payments")
@RequiredArgsConstructor
public class DemoPaymentController {

    private final PaymentUseCase paymentUseCase;

    @Value("${toss.payment.client-key}")
    private String tossClientKey;

    @Value("${toss.payment.customer-key}")
    private String tossCustomerKey;


    @GetMapping("/{paymentId}/checkout")
    public String checkout(@PathVariable UUID paymentId, Model model){
        PaymentInfoResult info = paymentUseCase.getPaymentInfo(paymentId);
        model.addAttribute("PAYMENT_ID", info.paymentId());
        model.addAttribute("PAYMENT_AMOUNT", info.amount());
        model.addAttribute("TOSS_CLIENT_KEY", tossClientKey);
        model.addAttribute("TOSS_CUSTOMER_KEY", tossCustomerKey);

        return "checkout";
    }

    @GetMapping("/{paymentId}/success")
    public String successPage(@PathVariable UUID paymentId, Model model) {
        model.addAttribute("paymentId", paymentId);
        return "success";
    }

    @GetMapping("/{paymentId}/failure")
    public String failPage(@PathVariable UUID paymentId, Model model) {
        model.addAttribute("paymentId", paymentId);
        return "fail";
    }
}
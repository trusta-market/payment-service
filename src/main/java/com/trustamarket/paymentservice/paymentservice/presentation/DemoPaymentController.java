package com.trustamarket.paymentservice.paymentservice.presentation;

import com.trustamarket.paymentservice.paymentservice.application.port.PaymentUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@Controller
@RequestMapping("/demo/payments")
@RequiredArgsConstructor
public class DemoPaymentController {

    private final PaymentUseCase paymentUseCase;

    @GetMapping("/checkout")
    public String checkout(){
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
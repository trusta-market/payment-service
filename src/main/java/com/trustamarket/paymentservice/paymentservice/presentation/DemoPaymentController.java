package com.trustamarket.paymentservice.paymentservice.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.UUID;

@Controller
@RequestMapping("/demo/payments")
@RequiredArgsConstructor
public class DemoPaymentController {

    @Value("${TOSS_CLIENT_KEY}")
    private String tossClientKey;

    @Value("${TOSS_CUSTOMER_KEY}")
    private String tossCustomerKey;


    @GetMapping("/checkout")
    public String checkout(Model model){
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
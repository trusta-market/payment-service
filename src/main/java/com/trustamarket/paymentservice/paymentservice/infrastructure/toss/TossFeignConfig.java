package com.trustamarket.paymentservice.paymentservice.infrastructure.toss;

import feign.RequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class TossFeignConfig {

    @Value("${toss.payment.secret-key}")
    private String secretKey;

    @Bean
    public RequestInterceptor tossAuthInterceptor() {
        return template -> {
            String encodedKey = Base64.getEncoder()
                    .encodeToString((secretKey + ":").getBytes(StandardCharsets.UTF_8));

            template.header("Authorization", "Basic " + encodedKey);
            template.header("Content-Type", "application/json");
        };
    }
}
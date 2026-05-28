package com.trustamarket.paymentservice.paymentservice.payment.infrastructure.notification;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.util.Map;

@Slf4j
@Component
public class PaymentSlackNotificationClient {

    @Value("${notification.slack.webhook-url:}")
    private String webhookUrl;

    private final RestTemplate restTemplate;

    public PaymentSlackNotificationClient(RestTemplateBuilder builder) {
        this.restTemplate = builder
                .connectTimeout(Duration.ofSeconds(3))
                .readTimeout(Duration.ofSeconds(3))
                .build();
    }

    public void send(String message) {
        if (webhookUrl == null || webhookUrl.isBlank()) {
            log.warn("[Slack] webhook-url 미설정. message={}", message);
            return;
        }
        try {
            restTemplate.postForEntity(webhookUrl, Map.of("text", message), Void.class);
        } catch (Exception e) {
            log.error("[Slack] 알림 전송 실패. message={}", message, e);
        }
    }
}

package com.spring.internal_working.internal_work.payments;

import com.spring.internal_working.internal_work.entities.Order;

import java.util.Optional;

public interface PaymentGateway {
    CheckoutSession createCheckoutSession(Order order);
    Optional<PaymentResult> parseWebhookRequest(WebhookRequest webhookRequest);
}

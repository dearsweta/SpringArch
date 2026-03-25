package com.spring.internal_working.internal_work.services;

import com.spring.internal_working.internal_work.entities.Order;
import com.spring.internal_working.internal_work.entities.OrderItem;
import com.spring.internal_working.internal_work.entities.PaymentStatus;
import com.spring.internal_working.internal_work.exceptions.PaymentException;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.exception.StripeException;
import com.stripe.model.Event;
import com.stripe.model.PaymentIntent;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class StripePaymentGateway implements PaymentGateway {
    @Value("${websiteURL}")
    private String websiteURL;

    @Value("${stripe.webhookSecretKey}")
    private String webhookSecretKey;

    @Override
    public CheckoutSession createCheckoutSession(Order order) {
        try {
            System.out.println("Stripe success URL = " +
                    websiteURL + "/checkout-success?orderId=" + order.getId());

            var builder = SessionCreateParams.builder()
                    .setMode(SessionCreateParams.Mode.PAYMENT)
                    .setSuccessUrl(websiteURL + "/checkout-success?orderId=" + order.getId())
                    .setCancelUrl(websiteURL + "/checkout-cancel")
                    .putMetadata("order_id", order.getId().toString())
                    .setPaymentIntentData(
                            SessionCreateParams.PaymentIntentData.builder()
                                    .putMetadata("order_id", order.getId().toString())
                                    .build()
                    );;

            order.getItems().forEach(item -> {
                var lineItem = createLineItem(item);
                builder.addLineItem(lineItem);
            });

            var session = Session.create(builder.build());
            return new CheckoutSession(session.getUrl());
        } catch (StripeException ex) {
            System.out.println(ex.getMessage());
            throw new PaymentException();
        }
    }

    @Override
    public Optional<PaymentResult> parseWebhookRequest(WebhookRequest webhookRequest) {
        try {
            System.out.println("Stripe success URL = ");

            var payload = webhookRequest.getPayload();
            var signature = webhookRequest.getHeaders().get("Stripe-Signature");
            if (signature == null) {
                throw new PaymentException("Missing Stripe-Signature header");
            }
            System.out.println(webhookSecretKey);
            var event = Webhook.constructEvent(payload, signature, webhookSecretKey);
        //    Event event = null;
            System.out.println("Event tyep got "+event.getType());

//            try {
//                event = Webhook.constructEvent(payload, signature, webhookSecretKey);
//            } catch (Exception e) {
//                System.out.println("❌ FAILED at constructEvent");
//                e.printStackTrace();
//                return Optional.empty();
//            }
            return switch (event.getType()) {
                case "payment_intent.succeeded" ->
                        Optional.of(new PaymentResult(extractOrderId(event), PaymentStatus.PAID));

                case "payment_intent.payment_failed" ->
                        Optional.of(new PaymentResult(extractOrderId(event), PaymentStatus.FAILED));

                default -> Optional.empty();

            };
        }
        catch (SignatureVerificationException e) {
            throw new PaymentException("Invalid Signature");
        }
    }

    private Long extractOrderId(Event event) {
        var stripeObject = event.getDataObjectDeserializer().getObject().orElseThrow(
                () -> new PaymentException("Could not deserialize Stripe event .Check the SDK and API version"));
        var paymentIntent = (PaymentIntent) stripeObject;

        System.out.println("METADATA = " + paymentIntent.getMetadata());
        return Long.valueOf(paymentIntent.getMetadata().get("order_id"));

    }

    private SessionCreateParams.LineItem createLineItem(OrderItem item) {
        return SessionCreateParams.LineItem.builder()
                .setQuantity(Long.valueOf(item.getQuantity()))
                .setPriceData(createPriceData(item))
                .build();
    }

    private SessionCreateParams.LineItem.PriceData createPriceData(OrderItem item) {
        return SessionCreateParams.LineItem.PriceData.builder()
                .setCurrency("usd")
                .setUnitAmountDecimal(
                        item.getUnitPrice().multiply(BigDecimal.valueOf(100)))
                .setProductData(createProductData(item))
                .build();
    }

    private SessionCreateParams.LineItem.PriceData.ProductData createProductData(OrderItem item) {
        return SessionCreateParams.LineItem.PriceData.ProductData.builder()
                .setName(item.getProduct().getName())
                .build();
    }
}


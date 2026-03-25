package com.spring.internal_working.internal_work.services;

import com.spring.internal_working.internal_work.dtos.CheckOutRequest;
import com.spring.internal_working.internal_work.dtos.CheckOutResponse;
import com.spring.internal_working.internal_work.entities.Order;
import com.spring.internal_working.internal_work.entities.PaymentStatus;
import com.spring.internal_working.internal_work.exceptions.CartEmptyException;
import com.spring.internal_working.internal_work.exceptions.CartNotFoundException;
import com.spring.internal_working.internal_work.exceptions.PaymentException;
import com.spring.internal_working.internal_work.repositories.CartRepository;
import com.spring.internal_working.internal_work.repositories.OrderRepository;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.PaymentIntent;
import com.stripe.net.Webhook;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CheckoutService {
    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;
    private final AuthService authService;
    private final CartService cartService;
    private final PaymentGateway paymentGateway;



    @Transactional
    public CheckOutResponse checkOut(CheckOutRequest request)  {
        var cart = cartRepository.findByIdCustom(request.getCartId()).orElse(null);

        if(cart == null){
            throw new CartNotFoundException();
        }

        if(cart.isEmpty()){
            throw new CartEmptyException();
        }
        var order = Order.fromCart(cart,authService.getCurrentUser());

        orderRepository.save(order);

        //checkout session
        try{
            var session = paymentGateway.createCheckoutSession(order);


            cartService.deleteCart(cart.getId());

            return (new CheckOutResponse(order.getId(),session.getCheckoutUrl()));
        }
        catch(PaymentException ex){
            orderRepository.delete(order);
            throw ex;
        }
    }
    public void handleWebhookEvent(WebhookRequest webhookRequest) {
        paymentGateway
                .parseWebhookRequest(webhookRequest)
                .ifPresent(paymentResult -> {
                    var order =  orderRepository.findById(paymentResult.getOrderId()).orElseThrow();
                    order.setStatus(paymentResult.getPaymentStatus());
                    orderRepository.save(order);
                });

    }
}

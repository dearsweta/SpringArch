package com.spring.internal_working.internal_work.controller;

import  com.spring.internal_working.internal_work.dtos.CheckOutRequest;
import com.spring.internal_working.internal_work.dtos.CheckOutResponse;
import com.spring.internal_working.internal_work.dtos.ErrorDto;
import com.spring.internal_working.internal_work.exceptions.CartEmptyException;
import com.spring.internal_working.internal_work.exceptions.CartNotFoundException;
import com.spring.internal_working.internal_work.exceptions.PaymentException;
import com.spring.internal_working.internal_work.repositories.OrderRepository;
import com.spring.internal_working.internal_work.services.CheckoutService;
import com.spring.internal_working.internal_work.services.WebhookRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/checkout")
public class CheckOutController {
    private final CheckoutService  checkoutService;



    @PostMapping
    public CheckOutResponse checkOut(@Valid @RequestBody CheckOutRequest request){
        //video85
            return checkoutService.checkOut(request);
    }

    @PostMapping("/webhook")
    public void handleWebhook(
            @RequestBody String payload,
            @RequestHeader Map<String,String> headers

    ){
        System.out.println("🔥 WEBHOOK HIT");

        if (headers == null) {
            System.out.println("❌ Missing Stripe-Signature header");
        }


        checkoutService.handleWebhookEvent(new WebhookRequest(headers, payload));
    }

    @ExceptionHandler({CartNotFoundException.class, CartEmptyException.class})
    public ResponseEntity<ErrorDto> handleException(Exception ex){
        return ResponseEntity.badRequest().body(new ErrorDto(ex.getMessage()));
    }

    @ExceptionHandler(PaymentException.class)
    public ResponseEntity<?> handlePaymentException(Exception ex){
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorDto("Error creating a checkout session"));
    }
    }



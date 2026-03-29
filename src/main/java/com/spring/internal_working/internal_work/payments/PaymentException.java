package com.spring.internal_working.internal_work.payments;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class PaymentException extends RuntimeException {
    public PaymentException(String message) {
        super(message);
    }
}

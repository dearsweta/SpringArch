package com.spring.internal_working.internal_work.payments;

import com.spring.internal_working.internal_work.entities.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PaymentResult {
    private Long orderId;
    private PaymentStatus paymentStatus;


}

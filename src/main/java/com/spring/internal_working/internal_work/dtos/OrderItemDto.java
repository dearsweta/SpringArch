package com.spring.internal_working.internal_work.dtos;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderItemDto {
    private OrderProductDto  product;
    private Integer quantity;
    private BigDecimal totalPrice;

}

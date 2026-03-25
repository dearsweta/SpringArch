package com.spring.internal_working.internal_work.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class CheckOutRequest {
    @NotNull(message = "cart id is requires")
    private UUID cartId;
}

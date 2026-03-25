package com.spring.internal_working.internal_work.mappers;

import com.spring.internal_working.internal_work.dtos.OrderDto;
import com.spring.internal_working.internal_work.entities.Order;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderDto toDto(Order order);
}

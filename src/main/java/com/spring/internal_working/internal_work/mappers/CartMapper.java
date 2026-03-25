package com.spring.internal_working.internal_work.mappers;

import com.spring.internal_working.internal_work.dtos.CartDto;
import com.spring.internal_working.internal_work.dtos.CartItemDto;
import com.spring.internal_working.internal_work.entities.Cart;
import com.spring.internal_working.internal_work.entities.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CartMapper {

    @Mapping(target = "totalPrice", expression = "java(cart.getTotalPrice())")
    CartDto toCartDto(Cart cart);

    @Mapping(target = "totalPrice" ,expression = "java(cartItem.getTotalPrice())")
    CartItemDto toCartItemDto(CartItem cartItem);
}

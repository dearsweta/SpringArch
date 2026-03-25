package com.spring.internal_working.internal_work.services;

import com.spring.internal_working.internal_work.dtos.CartDto;
import com.spring.internal_working.internal_work.dtos.CartItemDto;
import com.spring.internal_working.internal_work.entities.Cart;
import com.spring.internal_working.internal_work.exceptions.CartNotFoundException;
import com.spring.internal_working.internal_work.exceptions.ProductNotFoundException;
import com.spring.internal_working.internal_work.mappers.CartMapper;
import com.spring.internal_working.internal_work.repositories.CartRepository;
import com.spring.internal_working.internal_work.repositories.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@AllArgsConstructor
@Service
public class CartService {
    private final CartRepository cartRepository;
    private final CartMapper cartMapper;
    private final ProductRepository productRepository;

    public CartDto createCart(){
        var cart = new Cart();
        cartRepository.save(cart);

        return cartMapper.toCartDto(cart);
    }

    public CartItemDto addProductToCart(UUID cartId , Long productId){
        var cart = cartRepository.findByIdCustom(cartId).orElse(null);
        if(cart == null){
            throw new CartNotFoundException();
        }

        var product = productRepository.findById(productId).orElse(null);
        if(product == null){
            throw new ProductNotFoundException();
        }
        var cartItem =  cart.addCartItem(product);

        cartRepository.save(cart);
        return cartMapper.toCartItemDto(cartItem);
    }

    public CartDto getCart(UUID cartId){
        var cart = cartRepository.findByIdCustom(cartId).orElse(null);
        if(cart == null){
            throw new CartNotFoundException();
        }
        return cartMapper.toCartDto(cart);

    }

    public CartItemDto updateCart(UUID cartId,Long productId, int quantity){
        var cart = cartRepository.findByIdCustom(cartId).orElse(null);
        if(cart == null){
            throw new CartNotFoundException();
        }

        var cartItem = cart.getCartItemByProductId(productId);
        if(cartItem == null) {
            throw new ProductNotFoundException();
        }
        cartItem.setQuantity(quantity);
        cartRepository.save(cart);
        return cartMapper.toCartItemDto(cartItem);
    }

    public void deleteCartItem(UUID cartId,Long productId){
        var cart = cartRepository.findByIdCustom(cartId).orElse(null);
        if(cart == null){
            throw new CartNotFoundException();
        }
        cart.removeCartItem(productId);
        cartRepository.save(cart);
    }

    public void deleteCart(UUID cartId){
        var cart = cartRepository.findByIdCustom(cartId).orElse(null);
        if(cart == null){
            throw new CartNotFoundException();
        }
        cart.clearCart();
        cartRepository.save(cart);
    }



}

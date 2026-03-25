package com.spring.internal_working.internal_work.controller;

import com.spring.internal_working.internal_work.dtos.AddItemToCartRequest;
import com.spring.internal_working.internal_work.dtos.CartDto;
import com.spring.internal_working.internal_work.dtos.CartItemDto;
import com.spring.internal_working.internal_work.dtos.UpdateCartItemRequest;
import com.spring.internal_working.internal_work.exceptions.CartNotFoundException;
import com.spring.internal_working.internal_work.exceptions.ProductNotFoundException;
import com.spring.internal_working.internal_work.services.CartService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;
import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping("/carts")
@Tag(name="Carts")
public class CartController {
    private CartService cartService;

    @PostMapping
    public ResponseEntity<CartDto> createCart(UriComponentsBuilder uriBuilder) {
        System.out.println("hi");
        var cartDto = cartService.createCart();
        var uri = uriBuilder.path("/carts/{id}").build(cartDto.getId());
        return ResponseEntity.created(uri).body(cartDto);

    }

    @PostMapping("/{cartId}/items")
    public ResponseEntity<CartItemDto> addProductToCart(@PathVariable UUID cartId, @RequestBody AddItemToCartRequest request) {
        var cartItemDto = cartService.addProductToCart(cartId, request.getProductId());
        return ResponseEntity.status(HttpStatus.CREATED).body(cartItemDto);
    }

    @GetMapping("/{cartId}")
    public ResponseEntity<CartDto> getCart(@PathVariable UUID cartId) {
        var cartDto = cartService.getCart(cartId);
        return ResponseEntity.ok(cartDto);
    }

    @PutMapping("/{cartId}/items/{productId}")
    public ResponseEntity<CartItemDto> updateCart(@PathVariable("cartId") UUID cartId,@PathVariable("productId") Long productId , @RequestBody UpdateCartItemRequest request) {
        var cartItemDto = cartService.updateCart(cartId, productId, request.getQuantity());
        return ResponseEntity.ok(cartItemDto);
    }

    @DeleteMapping("/{cartId}/items/{productId}")
    public ResponseEntity<?> deleteCartItem(@PathVariable("cartId") UUID cartId,@PathVariable("productId") Long productId) {
        cartService.deleteCartItem(cartId, productId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/{cartId}/items")
    public ResponseEntity<?> deleteCart(@PathVariable("cartId") UUID cartId){
        cartService.deleteCart(cartId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @ExceptionHandler(CartNotFoundException.class)
    public ResponseEntity<Map<String,String>> handleCartNotFound(){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error",":Cart not found"));
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<Map<String,String>> handleProductNotFound(){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error",":Product not found in the cart"));
    }
}


package com.spring.internal_working.internal_work.controller;

import com.spring.internal_working.internal_work.dtos.ErrorDto;
import com.spring.internal_working.internal_work.dtos.OrderDto;
import com.spring.internal_working.internal_work.exceptions.OrderNotFoundException;
import com.spring.internal_working.internal_work.mappers.OrderMapper;
import com.spring.internal_working.internal_work.repositories.OrderRepository;
import com.spring.internal_working.internal_work.services.AuthService;
import com.spring.internal_working.internal_work.services.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/orders")
public class OrderController {

    private OrderService orderService;

    @GetMapping
    public List<OrderDto> getAllOrders(){
        return orderService.getAllOrders();
    }

    @GetMapping("/{orderId}")
    public OrderDto getOrderById(@PathVariable("orderId") Long orderId){
       return orderService.getOrderById(orderId);
    }

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<Void> handleOrderNotFoundException(){
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorDto> handleAccessDeniedException(Exception ex){
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(new ErrorDto(ex.getMessage()));
    }

}

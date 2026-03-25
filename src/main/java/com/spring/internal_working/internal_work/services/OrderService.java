package com.spring.internal_working.internal_work.services;

import com.spring.internal_working.internal_work.dtos.OrderDto;
import com.spring.internal_working.internal_work.exceptions.OrderNotFoundException;
import com.spring.internal_working.internal_work.mappers.OrderMapper;
import com.spring.internal_working.internal_work.repositories.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class OrderService {
    private final AuthService authService;
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    public List<OrderDto> getAllOrders(){
        var user = authService.getCurrentUser();
        var orders = orderRepository.getOrdersByCustomer(user);

        return orders.stream().map(orderMapper::toDto).toList();
    }
    public OrderDto getOrderById( Long orderId){
        var order = orderRepository.getOrderWithItems(orderId)
                .orElseThrow(OrderNotFoundException::new);

        var user= authService.getCurrentUser();
        if(!order.isPlacesBy(user)){
            throw new AccessDeniedException("you dont have access to this order");
        }

        return orderMapper.toDto(order);
    }
}

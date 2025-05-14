package com.fatih.orderservice.service;

import com.fatih.orderservice.dto.OrderRequest;
import com.fatih.orderservice.dto.OrderResponse;

import java.util.List;

public interface OrderService {
    OrderResponse placeOrder(OrderRequest request, Long userId);
    List<OrderResponse> getOrdersByUser(Long userId);
}

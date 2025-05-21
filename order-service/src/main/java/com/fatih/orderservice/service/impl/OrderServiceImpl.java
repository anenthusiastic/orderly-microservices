package com.fatih.orderservice.service.impl;

import com.fatih.orderservice.client.ProductClient;
import com.fatih.orderservice.dto.*;
import com.fatih.orderservice.entity.Order;
import com.fatih.orderservice.entity.OrderItem;
import com.fatih.orderservice.event.OrderPlacedEvent;
import com.fatih.orderservice.exception.ProductNotFoundException;
import com.fatih.orderservice.repository.OrderRepository;
import com.fatih.orderservice.service.OrderPublisher;
import com.fatih.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final ProductClient productClient;
    private final OrderRepository orderRepository;
    private final OrderPublisher orderPublisher;

    @Override
    public OrderResponse createOrder(OrderRequest request, Long userId) {

        for (OrderItemRequest item : request.getItems()) {
            Boolean exists = productClient.productExists(item.getProductId());
            if (!Boolean.TRUE.equals(exists)) {
                throw new ProductNotFoundException("Product not found: " + item.getProductId());
            }
        }

        Order order = Order.builder()
                .userId(userId)
                .createdAt(LocalDateTime.now())
                .build();

        List<OrderItem> items = request.getItems().stream()
                .map(itemReq -> OrderItem.builder()
                        .productId(itemReq.getProductId())
                        .quantity(itemReq.getQuantity())
                        .order(order)
                        .build())
                .toList();

        order.setItems(items);

        Order saved = orderRepository.save(order);

        OrderPlacedEvent event = OrderPlacedEvent.builder()
                .orderId(saved.getId())
                .userId(order.getUserId())
                .items(order.getItems().stream()
                        .map(item -> OrderItemDto.builder()
                                .productId(item.getProductId())
                                .quantity(item.getQuantity())
                                .build())
                        .toList())
                .build();

        orderPublisher.publishOrderPlacedEvent(event);

        return OrderResponse.builder()
                .orderId(saved.getId())
                .userId(saved.getUserId())
                .createdAt(saved.getCreatedAt())
                .items(saved.getItems().stream()
                        .map(item -> OrderItemResponse.builder()
                                .productId(item.getProductId())
                                .quantity(item.getQuantity())
                                .build())
                        .toList())
                .build();
    }

    @Override
    public List<OrderResponse> getOrdersByUser(Long userId) {
        return List.of();
    }
}

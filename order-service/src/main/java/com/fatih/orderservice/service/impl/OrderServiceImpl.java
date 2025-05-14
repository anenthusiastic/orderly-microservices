package com.fatih.orderservice.service.impl;

import com.fatih.orderservice.client.ProductClient;
import com.fatih.orderservice.dto.OrderRequest;
import com.fatih.orderservice.dto.OrderResponse;
import com.fatih.orderservice.dto.ProductResponse;
import com.fatih.orderservice.entity.Order;
import com.fatih.orderservice.event.OrderPlacedEvent;
import com.fatih.orderservice.exception.InsufficientStockException;
import com.fatih.orderservice.repository.OrderRepository;
import com.fatih.orderservice.service.OrderPublisher;
import com.fatih.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final ProductClient productClient;
    private final OrderRepository orderRepository;
    private final OrderPublisher orderPublisher;

    @Override
    public OrderResponse placeOrder(OrderRequest request, Long userId) {
        ProductResponse product = productClient.getProductById(request.getProductId());

        if (product.getQuantity() < request.getQuantity()) {
            throw new InsufficientStockException("yetersiz stok!.");
        }

        Order order = Order.builder()
                .productId(request.getProductId())
                .quantity(request.getQuantity())
                .userId(userId)
                .createdAt(LocalDateTime.now())
                .build();

        Order saved = orderRepository.save(order);

        orderPublisher.publishOrderPlacedEvent(
                new OrderPlacedEvent(order.getId(), order.getUserId(), "Sipariş başarıyla oluşturuldu.")
        );


        BigDecimal total = product.getPrice().multiply(BigDecimal.valueOf(request.getQuantity()));

        return OrderResponse.builder()
                .orderId(saved.getId())
                .productId(saved.getProductId())
                .userId(saved.getUserId())
                .quantity(saved.getQuantity())
                .totalPrice(total)
                .createdAt(saved.getCreatedAt())
                .build();
    }

    @Override
    public List<OrderResponse> getOrdersByUser(Long userId) {
        return List.of();
    }
}

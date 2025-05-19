package com.fatih.orderservice.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class OrderResponse {
    private Long orderId;
    private Long userId;
    private LocalDateTime createdAt;
    private List<OrderItemResponse> items;
}
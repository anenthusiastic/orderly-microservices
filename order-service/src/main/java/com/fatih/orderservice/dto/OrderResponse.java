package com.fatih.orderservice.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class OrderResponse {
    private Long orderId;
    private Long productId;
    private Long userId;
    private Integer quantity;
    private BigDecimal totalPrice;
    private LocalDateTime createdAt;
}
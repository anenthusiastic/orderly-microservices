package com.fatih.orderservice.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderItemResponse {
    private Long productId;
    private Integer quantity;
}

package com.fatih.notificationservice.event;

import com.fatih.notificationservice.dto.OrderItemDto;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderPlacedEvent {
    private Long orderId;
    private Long userId;
    private List<OrderItemDto> items;
}
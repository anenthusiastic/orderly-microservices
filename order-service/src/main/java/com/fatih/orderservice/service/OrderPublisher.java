package com.fatih.orderservice.service;

import com.fatih.orderservice.event.OrderPlacedEvent;

public interface OrderPublisher {
    void publishOrderPlacedEvent(OrderPlacedEvent event);
}

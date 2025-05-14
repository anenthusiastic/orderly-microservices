package com.fatih.orderservice.service.impl;

import com.fatih.orderservice.event.OrderPlacedEvent;
import com.fatih.orderservice.service.OrderPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderPublisherImpl implements OrderPublisher {

    private final RabbitTemplate rabbitTemplate;

    @Value("${order.exchange.name}")
    private String exchange;

    @Value("${order.routing.key}")
    private String routingKey;

    @Override
    public void publishOrderPlacedEvent(OrderPlacedEvent event) {
        rabbitTemplate.convertAndSend(exchange, routingKey, event);
    }
}

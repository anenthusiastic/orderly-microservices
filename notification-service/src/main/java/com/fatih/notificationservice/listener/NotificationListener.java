package com.fatih.notificationservice.listener;

import com.fatih.notificationservice.dto.OrderItemDto;
import com.fatih.notificationservice.event.OrderPlacedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class NotificationListener {

    @RabbitListener(queues = "${notification.queue.name}")
    public void handleOrderPlaced(OrderPlacedEvent event) {
        log.info("📦 Yeni sipariş! userId: {}", event.getUserId());
        for (OrderItemDto item : event.getItems()) {
            log.info("🔹 Ürün ID: {}, Adet: {}", item.getProductId(), item.getQuantity());
        }
    }

}

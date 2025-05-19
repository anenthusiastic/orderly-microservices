package com.fatih.productservice.listener;

import com.fatih.productservice.dto.OrderItemDto;
import com.fatih.productservice.event.OrderPlacedEvent;
import com.fatih.productservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ProductEventListener {

    private final ProductService productService;

    @RabbitListener(queues = "${product.queue.name}")
    public void handleOrderPlaced(OrderPlacedEvent event) {
        log.info("📦 Stok Güncellemesi Başladı → userId: {}", event.getUserId());
        for (OrderItemDto item : event.getItems()) {
            log.info("🔻 Ürün ID: {}, adet: {} stoğu düşülüyor", item.getProductId(), item.getQuantity());
            productService.decreaseStock(item.getProductId(), item.getQuantity());
        }
    }
}
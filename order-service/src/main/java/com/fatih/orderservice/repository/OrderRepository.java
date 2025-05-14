package com.fatih.orderservice.repository;

import com.fatih.orderservice.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
    // Gerekirse özel sorgular buraya eklenebilir
}

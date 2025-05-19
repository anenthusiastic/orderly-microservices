package com.fatih.orderservice.client;

import com.fatih.orderservice.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "product-service",configuration = FeignConfig.class)
public interface ProductClient {
    @GetMapping("/api/products/{id}/exists")
    Boolean productExists(@PathVariable("id") Long productId);
}

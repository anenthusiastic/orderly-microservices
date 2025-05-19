package com.fatih.orderservice.controller;

import com.fatih.orderservice.dto.OrderRequest;
import com.fatih.orderservice.dto.OrderResponse;
import com.fatih.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(@RequestBody OrderRequest orderRequest,
                                                     @AuthenticationPrincipal Jwt jwt) {
        Long userId = jwt.getClaim("userId");
        OrderResponse response = orderService.createOrder(orderRequest, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }
}

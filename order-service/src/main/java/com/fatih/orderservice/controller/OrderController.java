package com.fatih.orderservice.controller;

import com.fatih.orderservice.dto.OrderRequest;
import com.fatih.orderservice.dto.OrderResponse;
import com.fatih.orderservice.service.OrderService;
import com.fatih.orderservice.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final JwtUtil jwtUtil;

    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(
            @RequestBody OrderRequest orderRequest,
            HttpServletRequest request
    ) {
        String token = jwtUtil.extractTokenFromRequest(request);
        Long userId = jwtUtil.extractUserId(token);

        OrderResponse response = orderService.placeOrder(orderRequest, userId);
        return ResponseEntity.ok(response);
    }
}

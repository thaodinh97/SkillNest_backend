package com.example.skillnest.controllers;

import com.example.skillnest.dto.requests.OrderItemRequest;
import com.example.skillnest.dto.requests.OrderRequest;
import com.example.skillnest.dto.responses.ApiResponse;
import com.example.skillnest.dto.responses.OrderResponse;
import com.example.skillnest.services.OrderService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/v1/order")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderController {
    OrderService orderService;

    @PostMapping("/cart")
    public ApiResponse<OrderResponse> getOrCreateCart() {
        return ApiResponse.<OrderResponse>builder()
                .code(1000)
                .result(orderService.getOrCreateCart())
                .build();
    }

    @PostMapping("/{orderId}/items")
    public ApiResponse<OrderResponse> addItem(@PathVariable String orderId, @RequestBody OrderItemRequest request) {
        return ApiResponse.<OrderResponse>builder()
                .code(1000)
                .result(orderService.addItem(UUID.fromString(orderId),request))
                .build();
    }
}

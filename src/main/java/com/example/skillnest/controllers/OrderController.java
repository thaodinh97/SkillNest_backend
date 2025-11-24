package com.example.skillnest.controllers;

import com.example.skillnest.dto.requests.OrderItemRequest;
import com.example.skillnest.dto.requests.OrderRequest;
import com.example.skillnest.dto.responses.ApiResponse;
import com.example.skillnest.dto.responses.OrderResponse;
import com.example.skillnest.entity.Order;
import com.example.skillnest.services.OrderService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.weaver.ast.Or;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/v1/order")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderController {
    OrderService orderService;

    @PostMapping("/checkout")
    public ApiResponse<OrderResponse> checkout() {
        return ApiResponse.<OrderResponse>builder()
                .code(1000)
                .result(orderService.checkout())
                .build();
    }

    @GetMapping
    public ApiResponse<List<OrderResponse>> getOrderByUserId() {
        return ApiResponse.<List<OrderResponse>>builder()
                .code(1000)
                .result(orderService.getOrdersByUserId())
                .build();
    }


}

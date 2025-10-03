package com.example.skillnest.dto.responses;

import com.example.skillnest.entity.OrderItem;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderResponse {
    private UUID id;
    private String status;
    private Double totalPrice;
    private LocalDateTime createdAt;
    private List<OrderItemResponse> orderItems;
}

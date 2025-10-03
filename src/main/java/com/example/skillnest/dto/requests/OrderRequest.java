package com.example.skillnest.dto.requests;

import com.example.skillnest.entity.OrderItem;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderRequest {
    List<OrderItem> orderItems;
}

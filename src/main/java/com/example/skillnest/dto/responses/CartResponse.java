package com.example.skillnest.dto.responses;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CartResponse {
    UUID id;
    UUID userId;
    List<CartItemResponse> items;
    Double totalPrice;
}

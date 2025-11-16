package com.example.skillnest.dto.requests;

import com.example.skillnest.entity.CartItem;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CartRequest {
    String userId;
    List<CartItem> cartItems;
}

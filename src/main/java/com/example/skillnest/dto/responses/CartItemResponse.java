package com.example.skillnest.dto.responses;

import com.example.skillnest.entity.CartItem;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CartItemResponse {
    UUID id;
    UUID courseId;
    String courseName;
    String courseThumbnail;
    String courseInstructor;
    Double price;
}

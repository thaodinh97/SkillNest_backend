package com.example.skillnest.dto.responses;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderItemResponse {
    UUID id;
    UUID courseId;
    String courseName;
    String courseThumbnail;
    String courseInstructor;
    Double price;
}

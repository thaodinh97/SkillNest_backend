package com.example.skillnest.dto.requests;

import java.math.BigDecimal;
import java.util.UUID;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateCourseRequest {
    String title;
    String description;
    BigDecimal price;
    UUID instructorId;
}

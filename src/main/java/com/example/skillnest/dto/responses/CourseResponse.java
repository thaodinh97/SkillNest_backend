package com.example.skillnest.dto.responses;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CourseResponse {
    UUID id;
    String title;
    String description;
    BigDecimal price;
    UUID instructorId;
    String instructorName;
}

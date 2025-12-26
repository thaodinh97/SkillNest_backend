package com.example.skillnest.dto.responses;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CourseResponse {
    UUID id;
    String title;
    String description;
    Double price;
    Boolean isPublished;
    UUID instructorId;
    String instructorName;
    String thumbnailUrl;
    Long studentCount;
    List<SectionResponse> sections;
}

package com.example.skillnest.dto.responses;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SectionResponse {
    UUID id;
    String title;
    Integer order;
    CourseResponse course;
}

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
public class CourseSummaryResponse {
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

    public CourseSummaryResponse(UUID id, String title, String thumbnailUrl,
                                 Double price, Boolean isPublished, Long studentCount) {
        this.id = id;
        this.title = title;
        this.thumbnailUrl = thumbnailUrl;
        this.price = price;
        this.isPublished = isPublished;
        this.studentCount = studentCount;
    }
}

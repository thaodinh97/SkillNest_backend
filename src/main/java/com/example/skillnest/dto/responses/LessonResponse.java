package com.example.skillnest.dto.responses;

import com.example.skillnest.entity.Section;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LessonResponse {
    UUID id;
    String title;
    Integer order;
    String videoPublicId;
    String videoUrl;
    String content;
    UUID sectionId;
    String sectionTitle;
}

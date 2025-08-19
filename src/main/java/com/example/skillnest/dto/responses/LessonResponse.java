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
    String content;
    UUID sectionId;
    String sectionTitle;
}

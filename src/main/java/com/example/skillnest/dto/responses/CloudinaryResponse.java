package com.example.skillnest.dto.responses;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CloudinaryResponse {
    String publicId;
    String url;
}


package com.example.skillnest.dto.responses;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SignatureResponse {
    String signature;
    Long timestamp;
    String cloudName;
    String apiKey;
}

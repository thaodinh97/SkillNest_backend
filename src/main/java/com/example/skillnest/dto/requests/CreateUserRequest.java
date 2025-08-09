package com.example.skillnest.dto.requests;

import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateUserRequest {
    String fullName;
    String email;
    String role;
    @Size(min = 4, max = 50, message = "PASSWORD_INVALID")
    String password;
    LocalDateTime createdAt;
}

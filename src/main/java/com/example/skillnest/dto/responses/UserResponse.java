package com.example.skillnest.dto.responses;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse {
    UUID id;
    String fullName;
    String avatarUrl;
    String email;
    LocalDate dob;
    String phoneNumber;
    Set<RoleResponse> roles;
}

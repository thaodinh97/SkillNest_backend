package com.example.skillnest.dto.requests;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateUserRequest {
    String fullName;
    String email;
    @JsonFormat(pattern = "dd/MM/yyyy")
    LocalDate dob;
    List<String> roles;
}

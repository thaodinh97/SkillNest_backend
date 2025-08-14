package com.example.skillnest.dto.requests;

import com.example.skillnest.entity.Role;
import com.example.skillnest.validator.DobConstraint;
import com.example.skillnest.validator.EmailConstraint;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateUserRequest {
    String fullName;
    @EmailConstraint(message = "INVALID_EMAIL")
    String email;
    @Size(min = 4, max = 50, message = "PASSWORD_INVALID")
    String password;
    @DobConstraint(min = 18, message = "INVALID_DOB")
    @JsonFormat(pattern = "dd/MM/yyyy")
    LocalDate dob;
}

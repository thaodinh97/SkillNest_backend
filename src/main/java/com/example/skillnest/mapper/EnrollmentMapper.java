package com.example.skillnest.mapper;

import com.example.skillnest.dto.requests.EnrollmentRequest;
import com.example.skillnest.dto.responses.EnrollmentResponse;
import com.example.skillnest.entity.Enrollment;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EnrollmentMapper {
    Enrollment toEnrollment(EnrollmentRequest request);

    EnrollmentResponse toEnrollmentResponse(Enrollment enrollment);
}

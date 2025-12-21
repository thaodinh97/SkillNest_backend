package com.example.skillnest.mapper;

import com.example.skillnest.dto.requests.EnrollmentRequest;
import com.example.skillnest.dto.responses.EnrolledCourseResponse;
import com.example.skillnest.dto.responses.EnrollmentResponse;
import com.example.skillnest.entity.Enrollment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = CourseMapper.class)
public interface EnrollmentMapper {
    Enrollment toEnrollment(EnrollmentRequest request);

    @Mapping(source = "course", target = "course")
    EnrollmentResponse toEnrollmentResponse(Enrollment enrollment);

    @Mapping(source = "course", target = "course")
    @Mapping(source = "progressPercentage", target = "progressPercentage")
    EnrolledCourseResponse toEnrolledCourseResponse(Enrollment enrollment);
}

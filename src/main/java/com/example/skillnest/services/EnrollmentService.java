package com.example.skillnest.services;

import com.example.skillnest.dto.requests.EnrollmentRequest;
import com.example.skillnest.dto.responses.CheckEnrolledResponse;
import com.example.skillnest.dto.responses.EnrollmentResponse;
import com.example.skillnest.entity.Course;
import com.example.skillnest.entity.Enrollment;
import com.example.skillnest.entity.User;
import com.example.skillnest.exception.AppException;
import com.example.skillnest.exception.ErrorCode;
import com.example.skillnest.mapper.EnrollmentMapper;
import com.example.skillnest.repositories.CourseRepository;
import com.example.skillnest.repositories.EnrollmentRepository;
import com.example.skillnest.repositories.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EnrollmentService {
    CourseRepository courseRepository;
    UserRepository userRepository;
    EnrollmentMapper enrollmentMapper;
    EnrollmentRepository enrollmentRepository;
    public EnrollmentResponse enrollCourse(EnrollmentRequest enrollmentRequest) {
        Course course = courseRepository.findById(UUID.fromString(enrollmentRequest.getCourseId()))
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));
        var context = SecurityContextHolder.getContext();
        String userId = context.getAuthentication().getName();
        User user = userRepository.findById(UUID.fromString(userId))
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        boolean isEnrolled = enrollmentRepository.existsByUserAndCourse(user, course);
        if (isEnrolled) {
            throw new AppException(ErrorCode.USER_HAS_ENROLLED);
        }
        Enrollment enrollment = enrollmentMapper.toEnrollment(enrollmentRequest);
        enrollment.setCourse(course);
        enrollment.setUser(user);
        enrollment.setEnrolledAt(LocalDateTime.now());
        enrollmentRepository.save(enrollment);
        return enrollmentMapper.toEnrollmentResponse(enrollment);
    }
//    public Boolean checkEnrollment(EnrollmentRequest enrollmentRequest) {
//        Course course = courseRepository.findById(UUID.fromString(enrollmentRequest.getCourseId()))
//                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));
//        Boolean isEnrolled = enrollmentRepository.existsByUserAndCourse(user, course);
//        return isEnrolled;
//    }
}

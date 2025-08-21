package com.example.skillnest.services;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import jakarta.transaction.Transactional;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.example.skillnest.dto.requests.CreateCourseRequest;
import com.example.skillnest.dto.requests.UpdateCourseRequest;
import com.example.skillnest.dto.responses.CourseResponse;
import com.example.skillnest.dto.responses.DeleteCourseResponse;
import com.example.skillnest.entity.Course;
import com.example.skillnest.entity.User;
import com.example.skillnest.exception.AppException;
import com.example.skillnest.exception.ErrorCode;
import com.example.skillnest.mapper.CourseMapper;
import com.example.skillnest.repositories.CourseRepository;
import com.example.skillnest.repositories.UserRepository;
import com.example.skillnest.utils.JwtUtil;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CourseService {

    CourseRepository courseRepository;
    JwtUtil jwtUtil;
    UserRepository userRepository;
    CourseMapper courseMapper;

    @PreAuthorize("hasAuthority('CREATE_COURSE')")
    @Transactional
    public CourseResponse createCourse(CreateCourseRequest request) {

        User instructor = userRepository.findById(UUID.fromString(request.getInstructorId()))
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        if (instructor == null) {
            throw new AppException(ErrorCode.USER_NOT_FOUND);
        }
        Course course = courseMapper.toCourse(request);
        course.setInstructor(instructor);
        Course savedCourse = courseRepository.save(course);
        return courseMapper.toCourseResponse(savedCourse);
    }

    @Transactional
    public List<CourseResponse> getAllCourses() {
        return courseRepository.findAll().stream()
                .map(courseMapper::toCourseResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public CourseResponse getCourseById(UUID id) {
        return courseRepository
                .findById(id)
                .map(courseMapper::toCourseResponse)
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));
    }

    @PreAuthorize("hasAuthority('UPDATE_COURSE')")
    @Transactional
    public CourseResponse updateCourseById(String id, UpdateCourseRequest request) {
        log.info("Request to update course: {}", request);
        UUID courseId = UUID.fromString(id);
        var course =
                courseRepository.findById(courseId).orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));
        courseMapper.toUpdateCourse(request, course);
        User instructor = userRepository.findById(UUID.fromString(request.getInstructorId()))
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        course.setInstructor(instructor);
        courseRepository.save(course);
        return courseRepository.findById(courseId).stream()
                .map(courseMapper::toCourseResponse)
                .toList()
                .getFirst();
    }

    @Transactional
    public DeleteCourseResponse deleteCourseById(String id) {
        UUID courseId = UUID.fromString(id);
        courseRepository.deleteById(courseId);
        return DeleteCourseResponse.builder().isDeleted(true).build();
    }
}

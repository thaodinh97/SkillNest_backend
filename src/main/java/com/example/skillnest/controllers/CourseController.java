package com.example.skillnest.controllers;

import java.util.List;
import java.util.UUID;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.example.skillnest.dto.requests.CreateCourseRequest;
import com.example.skillnest.dto.requests.UpdateCourseRequest;
import com.example.skillnest.dto.responses.ApiResponse;
import com.example.skillnest.dto.responses.CourseResponse;
import com.example.skillnest.dto.responses.DeleteCourseResponse;
import com.example.skillnest.entity.Course;
import com.example.skillnest.services.CourseService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/api/v1/course")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CourseController {
    CourseService courseService;

    @PostMapping("/")
    public ApiResponse<CourseResponse> createCourse(@RequestBody CreateCourseRequest request) {
        ApiResponse<CourseResponse> response = new ApiResponse<>();
        response.setResult(courseService.createCourse(request));
        return response;
    }

    @GetMapping
    public ApiResponse<List<CourseResponse>> getAllCourse(@RequestParam(required = false) UUID instructorId) {

        ApiResponse<List<CourseResponse>> response = new ApiResponse<>();
        if (instructorId != null) {
            return ApiResponse.<List<CourseResponse>>builder()
                    .code(1000)
                    .result(courseService.getCourseByInstructorId(instructorId))
                    .build();
        }
        response.setResult(courseService.getAllCourses());
        return response;
    }

    @GetMapping("/{id}")
    public ApiResponse<CourseResponse> getCourseById(@PathVariable UUID id, HttpServletRequest httpServletRequest) {
        ApiResponse<CourseResponse> response = new ApiResponse<>();
        response.setResult(courseService.getCourseById(id));
        return response;
    }

    @PutMapping("/{id}")
    public ApiResponse<CourseResponse> updateCourse(
            @PathVariable String id, @RequestBody UpdateCourseRequest request, HttpServletRequest httpServletRequest) {
        ApiResponse<CourseResponse> response = new ApiResponse<>();
        response.setResult(courseService.updateCourseById(id, request));
        return response;
    }

    @DeleteMapping("/{id}")
    public ApiResponse<DeleteCourseResponse> deleteCourse(
            @PathVariable String id, HttpServletRequest httpServletRequest) {
        ApiResponse<DeleteCourseResponse> response = new ApiResponse<>();
        response.setResult(courseService.deleteCourseById(id));
        return response;
    }
}

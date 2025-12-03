package com.example.skillnest.controllers;

import com.example.skillnest.dto.requests.EnrollmentRequest;
import com.example.skillnest.dto.responses.ApiResponse;
import com.example.skillnest.dto.responses.CheckEnrolledResponse;
import com.example.skillnest.dto.responses.EnrollmentResponse;
import com.example.skillnest.services.EnrollmentService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/enroll")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EnrollController {
    EnrollmentService enrollmentService;

    @PostMapping
    public ApiResponse<EnrollmentResponse> enrollCourse(@RequestBody EnrollmentRequest enrollmentRequest) {
        return ApiResponse.<EnrollmentResponse>builder()
                .code(1000)
                .result(enrollmentService.enrollCourse(enrollmentRequest))
                .build();
    }

//    @PostMapping("/check")
//    public ApiResponse<Boolean> checkEnrollment(@RequestBody EnrollmentRequest enrollmentRequest) {
//        return ApiResponse.<Boolean>builder()
//                .code(1000)
//                .result(enrollmentService.checkEnrollment(enrollmentRequest))
//                .build();
//    }
}

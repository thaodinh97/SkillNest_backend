package com.example.skillnest.controllers;

import com.example.skillnest.dto.requests.SignatureRequest;
import com.example.skillnest.dto.responses.ApiResponse;
import com.example.skillnest.dto.responses.ImageUploadResponse;
import com.example.skillnest.dto.responses.SignatureResponse;
import com.example.skillnest.services.CloudinaryService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/cloudinary")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CloudinaryController {
    CloudinaryService cloudinaryService;

    @GetMapping("/signature")
    public ApiResponse<SignatureResponse> getSignature(
            @RequestParam(name = "course") String courseId,
            @RequestParam(name = "section") String sectionId,
            @RequestParam(name = "lesson") String lessonId
    ) {
        return ApiResponse.<SignatureResponse>builder()
                .code(1000)
                .result(cloudinaryService.getSignature(courseId, sectionId, lessonId))
                .build();
    }

    @PostMapping("/upload-avatar/{userId}")
    public ApiResponse<ImageUploadResponse> uploadAvatar(
            @PathVariable String userId,
            @RequestParam("file") MultipartFile file
    ) {
        String folder = "avatars/user_" + userId;
        String publicId = "avatar";

        return ApiResponse.<ImageUploadResponse>builder()
                .code(1000)
                .result(cloudinaryService.uploadImage(file, folder, publicId))
                .build();
    }

    @PostMapping("/upload-image/{courseId}")
    public ApiResponse<ImageUploadResponse> uploadCourseThumbnail(
            @PathVariable String courseId,
            @RequestParam("file") MultipartFile file
    ) {
        String folder = "courses/course_" + courseId;
        String publicId = "thumbnail";

        return ApiResponse.<ImageUploadResponse>builder()
                .code(1000)
                .result(cloudinaryService.uploadImage(file, folder, publicId))
                .build();
    }
}

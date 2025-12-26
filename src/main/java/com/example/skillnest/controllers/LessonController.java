package com.example.skillnest.controllers;

import com.example.skillnest.dto.requests.LessonRequest;
import com.example.skillnest.dto.requests.UpdateLessonRequest;
import com.example.skillnest.dto.responses.ApiResponse;
import com.example.skillnest.dto.responses.LessonResponse;
import com.example.skillnest.services.LessonService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/lesson")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class LessonController {
    LessonService lessonService;

    @PostMapping
    public ApiResponse<LessonResponse> createLesson(@RequestBody LessonRequest lessonRequest) {
        return ApiResponse.<LessonResponse>builder()
                .code(1000)
                .result(lessonService.createLesson(lessonRequest))
                .build();
    }

    @GetMapping
    public ApiResponse<List<LessonResponse>> getAllLessons() {
        return ApiResponse.<List<LessonResponse>>builder()
                .code(1000)
                .result(lessonService.getAllLessons())
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<LessonResponse> getLessonById(@PathVariable String id) {
        return ApiResponse.<LessonResponse>builder()
                .code(1000)
                .result(lessonService.getLessonById(id))
                .build();
    }

    @PatchMapping("/{id}")
    public ApiResponse<LessonResponse> updateLesson(@PathVariable String id, @RequestBody UpdateLessonRequest lessonRequest) {
        return ApiResponse.<LessonResponse>builder()
                .code(1000)
                .result(lessonService.updateLesson(id, lessonRequest))
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteLessonById(@PathVariable String id) {
        lessonService.deleteLessonById(id);
        return ApiResponse.<Void>builder()
                .code(1000)
                .message("Lesson has been deleted")
                .build();
    }
}

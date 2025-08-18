package com.example.skillnest.controllers;

import com.example.skillnest.dto.requests.SectionRequest;
import com.example.skillnest.dto.requests.UpdateSectionRequest;
import com.example.skillnest.dto.responses.ApiResponse;
import com.example.skillnest.dto.responses.SectionResponse;
import com.example.skillnest.services.SectionService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/section")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SectionController {
    SectionService sectionService;
    @PostMapping
    public ApiResponse<SectionResponse> createSection( @RequestBody SectionRequest request)
    {
        log.info("Create Section Request: {}", request);
        return ApiResponse.<SectionResponse>builder()
                .code(1000)
                .result(sectionService.createSection(request))
                .build();
    }

    @GetMapping
    public ApiResponse<List<SectionResponse>> getAllSections()
    {
        return ApiResponse.<List<SectionResponse>>builder()
                .code(1000)
                .result(sectionService.getAllSections())
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<SectionResponse> getSectionById(@PathVariable String id)
    {
        return ApiResponse.<SectionResponse>builder()
                .code(1000)
                .result(sectionService.getSection(id))
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<SectionResponse> updateSection(@PathVariable String id, @RequestBody UpdateSectionRequest request)
    {
        return ApiResponse.<SectionResponse>builder()
                .code(1000)
                .result(sectionService.updateSection(id, request))
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteSection(@PathVariable String id)
    {
        sectionService.delteSection(id);
        return ApiResponse.<Void>builder()
                .code(1000)
                .message("Section with id " + id + " was deleted")
                .build();
    }
}

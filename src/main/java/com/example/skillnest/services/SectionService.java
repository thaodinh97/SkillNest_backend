package com.example.skillnest.services;

import com.example.skillnest.dto.requests.SectionRequest;
import com.example.skillnest.dto.requests.UpdateSectionRequest;
import com.example.skillnest.dto.responses.SectionResponse;
import com.example.skillnest.entity.Course;
import com.example.skillnest.entity.CourseSection;
import com.example.skillnest.exception.AppException;
import com.example.skillnest.exception.ErrorCode;
import com.example.skillnest.mapper.SectionMapper;
import com.example.skillnest.repositories.CourseRepository;
import com.example.skillnest.repositories.SectionRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SectionService {
    SectionRepository sectionRepository;
    SectionMapper sectionMapper;
    CourseRepository courseRepository;

    @PreAuthorize("hasAuthority('CREATE_SECTION')")
    public SectionResponse createSection(SectionRequest request) {
        Course course = courseRepository.findById(UUID.fromString(request.getCourseId()))
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));

        CourseSection courseSection = sectionMapper.toCourseSection(request);
        courseSection.setCourse(course);
        sectionRepository.save(courseSection);

        return sectionMapper.toSectionResponse(courseSection);
    }

    public List<SectionResponse> getAllSections() {
        return sectionRepository.findAll()
                .stream()
                .map(sectionMapper::toSectionResponse)
                .toList();
    }

    public SectionResponse getSection(String sectionId) {
        return sectionRepository
                .findById(UUID.fromString(sectionId))
                .map(sectionMapper::toSectionResponse)
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));
    }

    public SectionResponse updateSection(String sectionId, UpdateSectionRequest request) {
        UUID id = UUID.fromString(sectionId);
        CourseSection section = sectionRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));
        sectionMapper.updateSection(request, section);
        sectionRepository.save(section);
        return sectionMapper.toSectionResponse(section);
    }

    public void delteSection(String sectionId) {
        CourseSection section = sectionRepository.findById(UUID.fromString(sectionId))
                        .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));
        sectionRepository.deleteById(UUID.fromString(sectionId));
    }

}

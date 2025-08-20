package com.example.skillnest.services;

import com.example.skillnest.dto.requests.SectionRequest;
import com.example.skillnest.dto.requests.UpdateSectionRequest;
import com.example.skillnest.dto.responses.SectionResponse;
import com.example.skillnest.entity.Course;
import com.example.skillnest.entity.Section;
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

        Section section = sectionMapper.toCourseSection(request);
        section.setCourse(course);
        Section savedSection = sectionRepository.save(section);

        return sectionMapper.toSectionResponse(savedSection);
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
        Section section = sectionRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));
        sectionMapper.updateSection(request, section);
        Section savedSection = sectionRepository.save(section);
        return sectionMapper.toSectionResponse(savedSection);
    }

    public void deleteSection(String sectionId) {
        if(sectionRepository.existsById(UUID.fromString(sectionId))){
            throw new AppException(ErrorCode.PRODUCT_NOT_FOUND);
        }
        sectionRepository.deleteById(UUID.fromString(sectionId));
    }

}

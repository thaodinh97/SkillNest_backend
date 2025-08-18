package com.example.skillnest.mapper;

import com.example.skillnest.dto.requests.SectionRequest;
import com.example.skillnest.dto.requests.UpdateSectionRequest;
import com.example.skillnest.dto.responses.SectionResponse;
import com.example.skillnest.entity.CourseSection;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface SectionMapper {
    CourseSection toCourseSection(SectionRequest request);
    SectionResponse toSectionResponse(CourseSection courseSection);

    void updateSection(UpdateSectionRequest sectionRequest, @MappingTarget CourseSection section);
}

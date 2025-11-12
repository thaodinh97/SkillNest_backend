package com.example.skillnest.mapper;

import com.example.skillnest.dto.requests.SectionRequest;
import com.example.skillnest.dto.requests.UpdateSectionRequest;
import com.example.skillnest.dto.responses.SectionResponse;
import com.example.skillnest.entity.Section;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface SectionMapper {
    Section toCourseSection(SectionRequest request);

    @Mapping(source = "course.id", target = "courseId")
    @Mapping(source = "course.title", target = "courseTitle")
    @Mapping(target = "lessons", source = "lessons")
    SectionResponse toSectionResponse(Section section);

    void updateSection(UpdateSectionRequest sectionRequest, @MappingTarget Section section);
}

package com.example.skillnest.mapper;

import org.mapstruct.*;

import com.example.skillnest.dto.requests.CreateCourseRequest;
import com.example.skillnest.dto.requests.UpdateCourseRequest;
import com.example.skillnest.dto.responses.CourseResponse;
import com.example.skillnest.entity.Course;

@Mapper(componentModel = "spring")
public interface CourseMapper {
    Course toCourse(CreateCourseRequest request);

    @Mapping(source = "instructor.id", target = "instructorId")
    @Mapping(source = "instructor.fullName", target = "instructorName")
    @Mapping(target = "sections", source = "sections")
    @Mapping(target = "studentCount", source = "studentCount")
    CourseResponse toCourseResponse(Course course);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void toUpdateCourse(UpdateCourseRequest request, @MappingTarget Course course);
}

package com.example.skillnest.mapper;

import com.example.skillnest.dto.requests.CreateCourseRequest;
import com.example.skillnest.dto.requests.UpdateCourseRequest;
import com.example.skillnest.dto.responses.CourseResponse;
import com.example.skillnest.entity.Course;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CourseMapper {
    Course toCourse(CreateCourseRequest request);
    static CourseResponse toCourseResponse(Course course) {
        CourseResponse courseResponse = CourseResponse
                .builder()
                .id(course.getId())
                .title(course.getTitle())
                .description(course.getDescription())
                .instructorId(course.getInstructor().getId())
                .instructorName(course.getInstructor().getFullName())
                .build();
        return courseResponse;
    };
    void toUpdateCourse(UpdateCourseRequest request, @MappingTarget Course course);
}

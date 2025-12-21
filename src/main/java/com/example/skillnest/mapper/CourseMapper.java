package com.example.skillnest.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.example.skillnest.dto.requests.CreateCourseRequest;
import com.example.skillnest.dto.requests.UpdateCourseRequest;
import com.example.skillnest.dto.responses.CourseResponse;
import com.example.skillnest.entity.Course;

@Mapper(componentModel = "spring")
public interface CourseMapper {
    Course toCourse(CreateCourseRequest request);

//    static CourseResponse toCourseResponse(Course course) {
//        CourseResponse courseResponse = CourseResponse.builder()
//                .id(course.getId())
//                .title(course.getTitle())
//                .price(course.getPrice())
//                .description(course.getDescription())
//                .instructorId(course.getInstructor().getId())
//                .instructorName(course.getInstructor().getFullName())
//                .build();
//        return courseResponse;
//    }
//    ;

    @Mapping(source = "instructor.id", target = "instructorId")
    @Mapping(source = "instructor.fullName", target = "instructorName")
    @Mapping(target = "sections", source = "sections")
    CourseResponse toCourseResponse(Course course);


    void toUpdateCourse(UpdateCourseRequest request, @MappingTarget Course course);
}

package com.example.skillnest.mapper;

import com.example.skillnest.dto.requests.LessonRequest;
import com.example.skillnest.dto.requests.UpdateLessonRequest;
import com.example.skillnest.dto.responses.LessonResponse;
import com.example.skillnest.entity.Lesson;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface LessonMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "section", ignore = true)
    @Mapping(target = "progresses", ignore = true)
    Lesson toLesson(LessonRequest lessonRequest);

    @Mapping(source = "section.id", target = "sectionId")
    @Mapping(source = "section.title", target = "sectionTitle")
    LessonResponse toLessonResponse(Lesson lesson);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateLesson(UpdateLessonRequest request, @MappingTarget Lesson lesson);


}

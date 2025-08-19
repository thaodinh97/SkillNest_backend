package com.example.skillnest.services;

import com.example.skillnest.dto.requests.LessonRequest;
import com.example.skillnest.dto.requests.UpdateLessonRequest;
import com.example.skillnest.dto.responses.LessonResponse;
import com.example.skillnest.dto.responses.SectionResponse;
import com.example.skillnest.entity.Lesson;
import com.example.skillnest.entity.Section;
import com.example.skillnest.exception.AppException;
import com.example.skillnest.exception.ErrorCode;
import com.example.skillnest.mapper.LessonMapper;
import com.example.skillnest.mapper.SectionMapper;
import com.example.skillnest.repositories.LessonRepository;
import com.example.skillnest.repositories.SectionRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class LessonService {
    LessonRepository  lessonRepository;
    LessonMapper  lessonMapper;
    SectionRepository sectionRepository;
    public LessonResponse createLesson(LessonRequest lessonRequest) {
        Section section = sectionRepository.findById(UUID.fromString(lessonRequest.getSectionId()))
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));
        Lesson lesson = lessonMapper.toLesson(lessonRequest);
        lesson.setSection(section);
        lessonRepository.save(lesson);
        return lessonMapper.toLessonResponse(lesson);
    }

    public List<LessonResponse> getAllLessons() {
        return lessonRepository.findAll()
                .stream()
                .map(lessonMapper::toLessonResponse)
                .toList();
    }

    public LessonResponse getLessonById(String lessonId) {
        return lessonRepository.findById(UUID.fromString(lessonId))
                .map(lessonMapper::toLessonResponse)
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));
    }

    public LessonResponse updateLesson(String lessonId, UpdateLessonRequest request) {
        Lesson lesson = lessonRepository.findById(UUID.fromString(lessonId))
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));
        lessonMapper.updateLesson(request, lesson);

        lessonRepository.save(lesson);
        return lessonMapper.toLessonResponse(lesson);
    }

    public void deleteLessonById(String lessonId) {
        Lesson lesson = lessonRepository.findById(UUID.fromString(lessonId))
                        .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));
        lessonRepository.deleteById(UUID.fromString(lessonId));
    }

}

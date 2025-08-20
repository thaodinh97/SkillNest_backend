package com.example.skillnest.service;

import com.example.skillnest.dto.requests.SectionRequest;
import com.example.skillnest.dto.requests.UpdateSectionRequest;
import com.example.skillnest.dto.responses.SectionResponse;
import com.example.skillnest.entity.Course;
import com.example.skillnest.entity.Section;
import com.example.skillnest.exception.AppException;
import com.example.skillnest.repositories.CourseRepository;
import com.example.skillnest.repositories.SectionRepository;
import com.example.skillnest.services.SectionService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
@TestPropertySource("/test.properties")
public class SectionServiceTest {
    @Autowired
    private SectionService sectionService;

    @MockBean
    private SectionRepository sectionRepository;

    @MockBean
    private CourseRepository courseRepository;

    private SectionRequest  sectionRequest;
    private SectionResponse sectionResponse;
    private Section section;
    private Course course;
    private UpdateSectionRequest  updateSectionRequest;

    @BeforeEach
    public void setup() {
        sectionRequest = SectionRequest.builder()
                .title("Test Section")
                .order(2)
                .courseId("828a8905-0f9c-46e3-b2e7-e199cae94bdb")
                .build();
        updateSectionRequest = UpdateSectionRequest.builder()
                .title("Test Section updated")
                .order(3)
                .build();

        sectionResponse = SectionResponse.builder()
                .id(UUID.fromString("21773b08-009d-46c7-9dd4-89d6bebfeb4c"))
                .title("Test Section updated")
                .order(3)
                .courseId(UUID.fromString("828a8905-0f9c-46e3-b2e7-e199cae94bdb"))
                .courseTitle("Course 2 updated")
                .build();
        course = Course.builder()
                .id(UUID.fromString("828a8905-0f9c-46e3-b2e7-e199cae94bdb"))
                .title("Course 2 updated")
                .description("course IT")
                .price(49.99)
                .build();
        section = Section.builder()
                .id(UUID.fromString("21773b08-009d-46c7-9dd4-89d6bebfeb4c"))
                .title("Test Section")
                .order(2)
                .course(course)
                .build();
    }

    @Test
    @WithMockUser(username = "testuser@gmail.com", authorities = {"CREATE_SECTION"})
    void createSection_validRequest_success() {
        Mockito.when(courseRepository.findById(course.getId())).thenReturn(Optional.of(course));
        Mockito.when(sectionRepository.save(Mockito.any())).thenReturn(section);

        var response = sectionService.createSection(sectionRequest);

        Assertions.assertThat(response.getId()).isEqualTo(UUID.fromString("21773b08-009d-46c7-9dd4-89d6bebfeb4c"));
        Assertions.assertThat(response.getTitle()).isEqualTo("Test Section");
        Assertions.assertThat(response.getOrder()).isEqualTo(2);
        Assertions.assertThat(response.getCourseId()).isEqualTo(UUID.fromString("828a8905-0f9c-46e3-b2e7-e199cae94bdb"));
        Assertions.assertThat(response.getCourseTitle()).isEqualTo("Course 2 updated");
    }

    @Test
    @WithMockUser(username = "testuser@gmail.com", authorities = {"CREATE_SECTION"})
    void createUser_courseNotFound_fail() {
        Mockito.when(courseRepository.findById(course.getId())).thenReturn(Optional.ofNullable(null));
        Mockito.when(sectionRepository.save(Mockito.any())).thenReturn(section);
        var exception = assertThrows(AppException.class, () ->  sectionService.createSection(sectionRequest));
        Assertions.assertThat(exception.getErrorCode().getCode()).isEqualTo(1007);
    }

    @Test
    @WithMockUser(username = "testuser@gmail.com")
    void updateSection_validRequest_success() {
        Mockito.when(sectionRepository.findById(section.getId())).thenReturn(Optional.of(section));
        Mockito.when(sectionRepository.save(Mockito.any())).thenReturn(section);
        var response = sectionService.updateSection("21773b08-009d-46c7-9dd4-89d6bebfeb4c", updateSectionRequest);
        Assertions.assertThat(response.getId()).isEqualTo(UUID.fromString("21773b08-009d-46c7-9dd4-89d6bebfeb4c"));
        Assertions.assertThat(response.getTitle()).isEqualTo("Test Section updated");
        Assertions.assertThat(response.getOrder()).isEqualTo(3);
    }

    @Test
    @WithMockUser(username = "testuser@gmail.com")
    void updateSection_sectionNotFound_fail() {
        Mockito.when(sectionRepository.findById(section.getId())).thenReturn(Optional.ofNullable(null));
        Mockito.when(sectionRepository.save(Mockito.any())).thenReturn(section);
        var exception = assertThrows(AppException.class, () -> sectionService.updateSection( "21773b08-009d-46c7-9dd4-89d6bebfeb4c", updateSectionRequest));
        Assertions.assertThat(exception.getErrorCode().getCode()).isEqualTo(1007);
    }

    @Test
    @WithMockUser(username = "testuser@gmail.com")
    void deleteSection_sectionNotExisted_success() {
        Mockito.when(sectionRepository.existsById(UUID.fromString("21773b08-009d-46c7-9dd4-89d6bebfeb4c"))).thenReturn(true);
        var exception = assertThrows(AppException.class, () -> sectionService.deleteSection("21773b08-009d-46c7-9dd4-89d6bebfeb4c"));
        Assertions.assertThat(exception.getErrorCode().getCode()).isEqualTo(1007);
    }
}

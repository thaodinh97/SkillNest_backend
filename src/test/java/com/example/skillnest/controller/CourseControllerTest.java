package com.example.skillnest.controller;

import com.example.skillnest.controllers.CourseController;
import com.example.skillnest.dto.requests.CreateCourseRequest;
import com.example.skillnest.dto.responses.CourseResponse;
import com.example.skillnest.entity.Course;
import com.example.skillnest.services.CourseService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.UUID;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/test.properties")
public class CourseControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CourseService courseService;

    private Course course;
    private CreateCourseRequest createCourseRequest;
    private CourseResponse response;

    @BeforeEach
    void initData() {
        createCourseRequest = CreateCourseRequest.builder()
                .title("Test Course Title")
                .description("Test Course Description")
                .instructorId("4f4e-8042-1eac36c233b5")
                .price(50.00)
                .build();
        response = CourseResponse.builder()
                .id(UUID.fromString("eeae79df-a6a2-48bd-991d-6bb987063955"))
                .title("Test Course Title")
                .description("Test Course Description")
                .instructorName("Test Instructor Name")
                .instructorId(UUID.fromString("16eabbaf-c522-4f4e-8042-1eac36c233b5"))
                .price(50.00)
                .build();
    }

    @Test
    @WithMockUser(username = "testuser@gmail.com")
    void createCourse_validRequest_success() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        String content = mapper.writeValueAsString(createCourseRequest);
        Mockito.when(courseService.createCourse(createCourseRequest)).thenReturn(response);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/course/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value(1000))
                .andExpect(MockMvcResultMatchers.jsonPath("result.id").value("eeae79df-a6a2-48bd-991d-6bb987063955"))
                .andExpect(MockMvcResultMatchers.jsonPath("result.title").value("Test Course Title"))
                .andExpect(MockMvcResultMatchers.jsonPath("result.description").value("Test Course Description"))
                .andExpect(MockMvcResultMatchers.jsonPath("result.instructorId").value("16eabbaf-c522-4f4e-8042-1eac36c233b5"))
                .andExpect(MockMvcResultMatchers.jsonPath("result.instructorName").value("Test Instructor Name"));
    }
}

package com.example.skillnest.controller;

import com.example.skillnest.dto.requests.SectionRequest;
import com.example.skillnest.dto.requests.UpdateSectionRequest;
import com.example.skillnest.dto.responses.CourseResponse;
import com.example.skillnest.dto.responses.SectionResponse;
import com.example.skillnest.services.SectionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
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
@TestPropertySource("/application-test.properties")
public class SectionControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SectionService sectionService;

    private SectionRequest request;
    private UpdateSectionRequest requestUpdate;
    private SectionResponse response;
    private SectionResponse updatedResponse;
    private CourseResponse courseResponse;

    @BeforeEach
    void initData()
    {
        courseResponse = CourseResponse.builder()
                .id(UUID.fromString("828a8905-0f9c-46e3-b2e7-e199cae94bdb"))
                .price(49.99)
                .title("Course 2 updated")
                .description("course IT")
                .instructorId(UUID.fromString("16eabbaf-c522-4f4e-8042-1eac36c233b5"))
                .instructorName("Instructor 1")
                .build();
        request = SectionRequest.builder()
                .title("Test Title")
                .order(1)
                .courseId("828a8905-0f9c-46e3-b2e7-e199cae94bdb")
                .build();
        requestUpdate = UpdateSectionRequest.builder()
                .title("Test Title updated")
                .order(2)
                .build();
        response = SectionResponse.builder()
                .id(UUID.fromString("21773b08-009d-46c7-9dd4-89d6bebfeb4c"))
                .title("Test Title")
                .order(1)
                .courseId(UUID.fromString("828a8905-0f9c-46e3-b2e7-e199cae94bdb"))
                .courseTitle("Course 2 updated")
                .build();
        updatedResponse = SectionResponse.builder()
                .title("Test Title updated")
                .order(2)
                .courseId(UUID.fromString("828a8905-0f9c-46e3-b2e7-e199cae94bdb"))
                .courseTitle("Course 2 updated")
                .build();
    }

    @Test
    @WithMockUser(username = "testuser@gmail.com")
    void createSection_validRequest_success() throws Exception
    {
        ObjectMapper mapper = new ObjectMapper();
        String content = mapper.writeValueAsString(request);
        Mockito.when(sectionService.createSection(ArgumentMatchers.any())).thenReturn(response);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/section")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(content))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value(1000))
                .andExpect(MockMvcResultMatchers.jsonPath("result.title").value("Test Title"))
                .andExpect(MockMvcResultMatchers.jsonPath("result.courseId").value("828a8905-0f9c-46e3-b2e7-e199cae94bdb"));
    }

    @Test
    @WithMockUser(username = "testuser@gmail.com")
    void updateSection_validRequest_success() throws Exception
    {
        String sectionId = "21773b08-009d-46c7-9dd4-89d6bebfeb4c";
        ObjectMapper mapper = new ObjectMapper();
        String content = mapper.writeValueAsString(request);
        Mockito.when(sectionService.updateSection(ArgumentMatchers.eq(sectionId), ArgumentMatchers.any())).thenReturn(updatedResponse);
        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/section/{id}", sectionId)
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .content(content))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("code").value(1000))
        .andExpect(MockMvcResultMatchers.jsonPath("result.title").value("Test Title updated"))
                .andExpect(MockMvcResultMatchers.jsonPath("result.order").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("result.courseId").value("828a8905-0f9c-46e3-b2e7-e199cae94bdb"))
                .andExpect(MockMvcResultMatchers.jsonPath("result.courseTitle").value("Course 2 updated"));
    }
}

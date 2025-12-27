package com.example.skillnest.controller;

import java.time.LocalDate;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.example.skillnest.dto.requests.CreateUserRequest;
import com.example.skillnest.dto.responses.UserResponse;
import com.example.skillnest.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    private CreateUserRequest createUserRequest;
    private UserResponse userResponse;
    private LocalDate dob;

    @BeforeEach
    void initData() {

        dob = LocalDate.of(2005, 8, 8);
        createUserRequest = CreateUserRequest.builder()
                .email("test@gmail.com")
                .password("12345")
                .fullName("Test User")
                .dob(dob)
                .build();

        userResponse = UserResponse.builder()
                .id(UUID.fromString("78312e60-0fab-4b35-9a5a-8260503f0ee0"))
                .email("test@gmail.com")
                .fullName("Test User")
                .dob(dob)
                .build();
    }

    @Test
    void createUser_validRequest_success() throws Exception {
        // GIVEN
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        String content = mapper.writeValueAsString(createUserRequest);

        Mockito.when(userService.createRequest(ArgumentMatchers.any())).thenReturn(userResponse);

        // WHEN, THEN
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/user/")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(content))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value(1000))
                .andExpect(MockMvcResultMatchers.jsonPath("result.id").value("78312e60-0fab-4b35-9a5a-8260503f0ee0"));
    }

    @Test
    void createUser_emailInvalid_fail() throws Exception {
        // GIVEN
        createUserRequest.setEmail("testgmail.com");
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        String content = mapper.writeValueAsString(createUserRequest);

        // WHEN, THEN
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/user/")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(content))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value(1009))
                .andExpect(MockMvcResultMatchers.jsonPath("message").value("Please provide a valid email address!"));
    }
}

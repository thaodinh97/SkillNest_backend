package com.example.skillnest.service;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;

import com.example.skillnest.dto.requests.CreateUserRequest;
import com.example.skillnest.dto.responses.UserResponse;
import com.example.skillnest.entity.User;
import com.example.skillnest.exception.AppException;
import com.example.skillnest.repositories.UserRepository;
import com.example.skillnest.services.UserService;

@SpringBootTest
@TestPropertySource("/test.properties")
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    private CreateUserRequest createUserRequest;
    private UserResponse userResponse;
    private User user;
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

        user = User.builder()
                .id(UUID.fromString("78312e60-0fab-4b35-9a5a-8260503f0ee0"))
                .email("test@gmail.com")
                .fullName("Test User")
                .dob(dob)
                .build();
    }

    @Test
    void createUser_validRequest_success() {
        // GIVEN
        Mockito.when(userRepository.existsByEmail(Mockito.anyString())).thenReturn(false);
        Mockito.when(userRepository.save(Mockito.any())).thenReturn(user);
        // WHEN
        var response = userService.createRequest(createUserRequest);
        // THEN
        Assertions.assertThat(response.getId()).isEqualTo(UUID.fromString("78312e60-0fab-4b35-9a5a-8260503f0ee0"));
        Assertions.assertThat(response.getEmail()).isEqualTo("test@gmail.com");
    }

    @Test
    void createUser_userExisted_fail() {
        // GIVEN
        Mockito.when(userRepository.existsByEmail(Mockito.anyString())).thenReturn(true);
        // WHEN
        var exception = assertThrows(AppException.class, () -> userService.createRequest(createUserRequest));
        Assertions.assertThat(exception.getErrorCode().getCode()).isEqualTo(1002);
        Assertions.assertThat(exception.getMessage()).isEqualTo("User existed!");
    }

    @Test
    @WithMockUser(username = "test@gmail.com")
    void getMyInfo_validRequest_success() {
        Mockito.when(userRepository.findByEmail(Mockito.anyString())).thenReturn(Optional.of(user));
        var response = userService.getMyInfo();

        Assertions.assertThat(response.getId()).isEqualTo(UUID.fromString("78312e60-0fab-4b35-9a5a-8260503f0ee0"));
        Assertions.assertThat(response.getEmail()).isEqualTo("test@gmail.com");
    }

    @Test
    @WithMockUser(username = "test@gmail.com")
    void getMyInfo_userNotFound_error() {
        Mockito.when(userRepository.findByEmail(Mockito.anyString())).thenReturn(Optional.ofNullable(null));

        var exception = assertThrows(AppException.class, () -> userService.getMyInfo());

        Assertions.assertThat(exception.getErrorCode().getCode()).isEqualTo(1004);
    }
}

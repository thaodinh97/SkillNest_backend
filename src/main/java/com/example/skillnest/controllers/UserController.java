package com.example.skillnest.controllers;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.example.skillnest.dto.requests.CreateUserRequest;
import com.example.skillnest.dto.requests.UpdateUserRequest;
import com.example.skillnest.dto.responses.ApiResponse;
import com.example.skillnest.dto.responses.UserResponse;
import com.example.skillnest.entity.User;
import com.example.skillnest.services.UserService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {

    UserService userService;

    @PostMapping("/")
    ApiResponse<UserResponse> createUser(@RequestBody @Valid CreateUserRequest request) {
        log.info("Controller: create user");
        ApiResponse<UserResponse> response = new ApiResponse<>();
        response.setResult(userService.createRequest(request));
        return response;
    }

    @GetMapping("/")
    ApiResponse<List<User>> getUser() {
        ApiResponse<List<User>> response = new ApiResponse<>();
        response.setResult(userService.getAllUsers());
        return response;
    }

    @GetMapping("/instructors")
    ApiResponse<List<UserResponse>> getInstructors() {
        return ApiResponse.<List<UserResponse>>builder()
                .code(1000)
                .result(userService.getAllInstructors())
                .build();
    }

    @GetMapping("/{id}")
    ApiResponse<UserResponse> getUser(@PathVariable String id) {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("Username: {}", authentication.getName());
        log.info("Roles: {}", authentication.getAuthorities());
        ApiResponse<UserResponse> response = new ApiResponse<>();
        response.setResult(userService.getUserById(id));
        return response;
    }

    @GetMapping("/my-info")
    ApiResponse<UserResponse> getMyInfo() {
        return ApiResponse.<UserResponse>builder()
                .result(userService.getMyInfo())
                .build();
    }

    @PutMapping("/{id}")
    ApiResponse<User> updateUser(@PathVariable String id, @RequestBody UpdateUserRequest request) {
        ApiResponse<User> response = new ApiResponse<>();
        response.setResult(userService.updateUser(id, request));
        return response;
    }

    @DeleteMapping("/{id}")
    ApiResponse<String> deleteUser(@PathVariable String id) {
        ApiResponse<String> response = new ApiResponse<>();
        try {
            userService.deleteUser(id);
            response.setResult("User has been deleted successfully");
        } catch (Exception e) {
            response.setResult("error");
        }
        return response;
    }
}

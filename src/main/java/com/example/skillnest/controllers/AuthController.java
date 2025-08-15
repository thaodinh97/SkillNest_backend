package com.example.skillnest.controllers;

import com.example.skillnest.dto.requests.AuthRequest;
import com.example.skillnest.dto.requests.IntrospectRequest;
import com.example.skillnest.dto.requests.LogoutRequest;
import com.example.skillnest.dto.requests.RefreshRequest;
import com.example.skillnest.dto.responses.ApiResponse;
import com.example.skillnest.dto.responses.AuthResponse;
import com.example.skillnest.dto.responses.IntrospectResponse;
import com.example.skillnest.services.AuthService;
import com.nimbusds.jose.JOSEException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@Slf4j
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthController {
    AuthService authService;

    @PostMapping("/login")
    public ApiResponse<AuthResponse> login(@RequestBody AuthRequest authRequest) {
        log.info("Login request: {}", authRequest);
        var result = authService.authenticate(authRequest);
        ApiResponse<AuthResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(result);
        return apiResponse;
    }

    @PostMapping("/verify")
    public ApiResponse<IntrospectResponse> login(@RequestBody IntrospectRequest request)
            throws JOSEException, ParseException {
        ApiResponse<IntrospectResponse> apiResponse = new ApiResponse<>();
        var result = authService.introspectToken(request);
        apiResponse.setResult(result);
        return apiResponse;
    }

    @PostMapping("/logout")
    public ApiResponse<Void> logout(@RequestBody LogoutRequest request)
    throws JOSEException, ParseException {
        authService.logout(request);
        return ApiResponse.<Void>builder()
                .code(1000)
                .build();

    }

    @PostMapping("/refresh")
    public ApiResponse<AuthResponse> refresh(@RequestBody RefreshRequest request)
            throws JOSEException, ParseException {
        var result = authService.refreshToken(request);
        return ApiResponse.<AuthResponse>builder()
                .code(1000)
                .result(result)
                .build();
    }
}

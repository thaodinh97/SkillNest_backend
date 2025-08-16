package com.example.skillnest.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.example.skillnest.dto.requests.RoleRequest;
import com.example.skillnest.dto.responses.ApiResponse;
import com.example.skillnest.dto.responses.RoleResponse;
import com.example.skillnest.services.PermissionService;
import com.example.skillnest.services.RoleService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/api/v1/roles")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleController {
    PermissionService permissionService;
    RoleService roleService;

    @PostMapping("/")
    ApiResponse<RoleResponse> create(@RequestBody RoleRequest request) {

        return ApiResponse.<RoleResponse>builder()
                .code(1000)
                .result(roleService.create(request))
                .build();
    }

    @GetMapping("/")
    ApiResponse<List<RoleResponse>> getAll() {
        return ApiResponse.<List<RoleResponse>>builder()
                .code(1000)
                .result(roleService.getAll())
                .build();
    }

    @DeleteMapping("/{role}")
    ApiResponse<Void> delete(@PathVariable String role) {
        roleService.delete(role);
        return ApiResponse.<Void>builder().code(1000).build();
    }
}

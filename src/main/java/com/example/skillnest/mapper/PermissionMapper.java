package com.example.skillnest.mapper;

import org.mapstruct.Mapper;

import com.example.skillnest.dto.requests.PermissionRequest;
import com.example.skillnest.dto.responses.PermissionResponse;
import com.example.skillnest.entity.Permission;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    Permission toPermission(PermissionRequest request);

    PermissionResponse toResponse(Permission permission);
}

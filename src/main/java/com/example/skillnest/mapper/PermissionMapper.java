package com.example.skillnest.mapper;

import com.example.skillnest.dto.requests.PermissionRequest;
import com.example.skillnest.dto.responses.PermissionResponse;
import com.example.skillnest.entity.Permission;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    Permission toPermission(PermissionRequest request);
    PermissionResponse toResponse(Permission permission);
}

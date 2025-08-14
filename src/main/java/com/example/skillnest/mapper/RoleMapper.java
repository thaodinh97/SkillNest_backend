package com.example.skillnest.mapper;

import com.example.skillnest.dto.requests.RoleRequest;
import com.example.skillnest.dto.responses.RoleResponse;
import com.example.skillnest.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    @Mapping(target = "permissions", ignore = true)
    Role toRole(RoleRequest request);
    RoleResponse toResponse(Role role);
}

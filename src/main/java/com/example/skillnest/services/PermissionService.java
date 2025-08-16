package com.example.skillnest.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.skillnest.dto.requests.PermissionRequest;
import com.example.skillnest.dto.responses.PermissionResponse;
import com.example.skillnest.entity.Permission;
import com.example.skillnest.mapper.PermissionMapper;
import com.example.skillnest.repositories.PermissionRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PermissionService {
    PermissionRepository permissionRepository;
    PermissionMapper permissionMapper;

    public PermissionResponse create(PermissionRequest request) {
        Permission permission = permissionMapper.toPermission(request);
        permission = permissionRepository.save(permission);
        return permissionMapper.toResponse(permission);
    }

    public List<PermissionResponse> getAllPermissions() {
        List<PermissionResponse> permissions = permissionRepository.findAll().stream()
                .map(permissionMapper::toResponse)
                .toList();
        return permissions;
    }

    public void delete(String permission) {
        permissionRepository.deleteById(permission);
    }
}

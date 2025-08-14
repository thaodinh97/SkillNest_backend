package com.example.skillnest.services;


import com.example.skillnest.dto.requests.RoleRequest;
import com.example.skillnest.dto.responses.RoleResponse;
import com.example.skillnest.mapper.RoleMapper;
import com.example.skillnest.repositories.PermissionRepository;
import com.example.skillnest.repositories.RoleRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleService {
    RoleRepository roleRepository;
    PermissionRepository permissionRepository;
    RoleMapper roleMapper;

    public RoleResponse create(RoleRequest roleRequest) {
        var role = roleMapper.toRole(roleRequest);

        var permissions = permissionRepository.findAllById(roleRequest.getPermissions());
        role.setPermissions(new HashSet<>(permissions));
        role = roleRepository.save(role);

        return roleMapper.toResponse(role);
    }

    public List<RoleResponse> getAll() {
        return  roleRepository.findAll()
                .stream()
                .map(roleMapper::toResponse)
                .toList();
    }

    public void delete(String role)
    {
        roleRepository.deleteById(role);
    }
}

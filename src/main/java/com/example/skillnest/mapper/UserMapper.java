package com.example.skillnest.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.example.skillnest.dto.requests.CreateUserRequest;
import com.example.skillnest.dto.requests.UpdateUserRequest;
import com.example.skillnest.dto.responses.UserResponse;
import com.example.skillnest.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "fullName", source = "fullName")
    User toUser(CreateUserRequest request);

    UserResponse toUserResponse(User user);

    @Mapping(target = "roles", ignore = true)
    void updateUser(@MappingTarget User user, UpdateUserRequest request);
}

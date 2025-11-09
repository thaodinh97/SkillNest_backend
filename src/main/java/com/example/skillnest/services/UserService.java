package com.example.skillnest.services;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

import jakarta.transaction.Transactional;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.skillnest.dto.requests.CreateUserRequest;
import com.example.skillnest.dto.requests.UpdateUserRequest;
import com.example.skillnest.dto.responses.UserResponse;
import com.example.skillnest.entity.User;
import com.example.skillnest.exception.AppException;
import com.example.skillnest.exception.ErrorCode;
import com.example.skillnest.mapper.UserMapper;
import com.example.skillnest.repositories.RoleRepository;
import com.example.skillnest.repositories.UserRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {

    UserRepository userRepository;
    UserMapper userMapper;
    PasswordEncoder passwordEncoder;
    RoleRepository roleRepository;

    @Transactional
    public UserResponse createRequest(CreateUserRequest request) {
        log.info("Service: create user");
        log.debug("Creating request for user {}", request.getEmail());
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }

        User user = userMapper.toUser(request);
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setCreatedAt(LocalDateTime.now());

        User savedUser = userRepository.save(user);

        return userMapper.toUserResponse(savedUser);
    }

    //    @PreAuthorize("hasRole('admin')")
//    @PreAuthorize("hasAuthority('APPROVE_COURSE')")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @PostAuthorize("returnObject.email == authentication.name")
    @Transactional
    public UserResponse getUserById(String id) {
        UUID userId = UUID.fromString(id);
        return userMapper.toUserResponse(userRepository
                .findById(userId)
                .orElseThrow(() -> new RuntimeException("User with id " + id + " not found")));
    }

    public List<UserResponse> getUserByRoleName(String roleName) {
        return userRepository.findUsersByRoleName(roleName)
                .stream()
                .map(userMapper::toUserResponse)
                .toList();
    }

    @Transactional
    public UserResponse getMyInfo() {
        var context = SecurityContextHolder.getContext();
        String userId = context.getAuthentication().getName();
        User user = userRepository.findById(UUID.fromString(userId)).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        return userMapper.toUserResponse(user);
    }

    @Transactional
    public User updateUser(String id, UpdateUserRequest request) {
        UUID userId = UUID.fromString(id);
        User user = userRepository
                .findById(userId)
                .orElseThrow(() -> new RuntimeException("User with id " + id + " not found"));
        userMapper.updateUser(user, request);
        var roles = roleRepository.findAllById(request.getRoles());
        user.setRoles(new HashSet<>(roles));
        user.setDob(request.getDob());
        return userRepository.save(user);
    }

    @Transactional
    public void deleteUser(String id) {
        User user = userRepository
                .findById(UUID.fromString(id))
                .orElseThrow(() -> new RuntimeException("User with id " + id + " not found"));
        userRepository.delete(user);
    }
}

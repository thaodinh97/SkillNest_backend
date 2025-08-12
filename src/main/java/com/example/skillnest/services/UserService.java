package com.example.skillnest.services;

import com.example.skillnest.dto.requests.CreateUserRequest;
import com.example.skillnest.dto.requests.UpdateUserRequest;
import com.example.skillnest.dto.responses.UserResponse;
import com.example.skillnest.entity.User;
import com.example.skillnest.enums.Role;
import com.example.skillnest.exception.AppException;
import com.example.skillnest.exception.ErrorCode;
import com.example.skillnest.mapper.UserMapper;
import com.example.skillnest.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {

    UserRepository userRepository;
    UserMapper userMapper;

    @Transactional
    public User createRequest(CreateUserRequest request) {

        log.debug("Creating request for user {}", request.getEmail());
        if(userRepository.existsByEmail(request.getEmail())){
            throw new AppException(ErrorCode.USER_EXISTED);
        }

        User user = userMapper.toUser(request);
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.student.name());
        user.setCreatedAt(LocalDateTime.now());

        return userRepository.save(user);
    }

    @PreAuthorize("hasRole('admin')")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @PostAuthorize("returnObject.email == authentication.name")
    @Transactional
    public UserResponse getUserById(String id) {
        UUID userId = UUID.fromString(id);
        return userMapper.toUserResponse(userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User with id " + id + " not found")));
    }

    @Transactional
    public UserResponse getMyInfo() {
        var context = SecurityContextHolder.getContext();
        String email = context.getAuthentication().getName();
        User user = userRepository.findByEmail(email).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        return userMapper.toUserResponse(user);
    }

    @Transactional
    public User updateUser(String id, UpdateUserRequest request) {
        UUID userId = UUID.fromString(id);
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User with id " + id + " not found"));
        userMapper.updateUser(user, request);
        return userRepository.save(user);
    }

    @Transactional
    public void deleteUser(String id) {
        User user = userRepository.findById(UUID.fromString(id)).orElseThrow(() -> new RuntimeException("User with id " + id + " not found"));
        userRepository.delete(user);
    }
}

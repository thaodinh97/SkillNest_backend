package com.example.skillnest.config;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.skillnest.entity.User;
import com.example.skillnest.repositories.UserRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;

@Slf4j
@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ApplicationInitConfig {
    PasswordEncoder passwordEncoder;

    @Bean
    @ConditionalOnProperty(
            prefix = "spring",
            value = "datasource.driver-class-name",
            havingValue = "org.postgresql.Driver")
    ApplicationRunner applicationRunner(UserRepository userRepository) {
        log.info("Init Application Config ...");
        return args -> {
            if (userRepository.findByEmail("admin@gmail.com").isEmpty()) {
                User user = User.builder()
                        .email("admin@gmail.com")
                        .dob(LocalDate.now())
                        .fullName("Admin")
                        .password(passwordEncoder.encode("admin"))
                        .build();
                userRepository.save(user);
                log.warn("admin user has been created with default password");
            }
        };
    }
}

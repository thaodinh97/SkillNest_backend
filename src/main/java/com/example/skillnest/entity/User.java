package com.example.skillnest.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import jakarta.persistence.*;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(
            name = "id",
            updatable = false,
            nullable = false,
            columnDefinition = "UUID")
    UUID id;

    @Column(name = "full_name")
    String fullName;

    String email;

    String password;

    @Column(name = "date_of_birth")
    LocalDate dob;

    @Column(name = "phone_number")
    String phoneNumber;

    @ManyToMany
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "roles_name")
    )
    Set<Role> roles;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    List<Enrollment> enrollments;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    List<Order> orders;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    List<Review> reviews;

    @Column(name = "created_at")
    LocalDateTime createdAt;
}

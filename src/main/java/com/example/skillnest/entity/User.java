package com.example.skillnest.entity;

import com.example.skillnest.validator.EmailConstraint;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

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
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "UUID") // Added this for clarity and safety
    UUID id;

    @Column(name = "full_name")
    String fullName;

    String email;

    String password;

    @Column(name = "date_of_birth")
    LocalDate dob;

    @ManyToMany
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

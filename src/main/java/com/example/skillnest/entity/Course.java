package com.example.skillnest.entity;

import java.util.List;
import java.util.UUID;

import jakarta.persistence.*;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "courses")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "UUID")
    UUID id;

    @Setter
    @Column(nullable = false, length = 250)
    String title;

    @Setter
    @Column(columnDefinition = "TEXT")
    String description;

    @Setter
    @Column(nullable = false)
    Double price;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "instructor_id", nullable = false)
    User instructor;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    List<Section> sections;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    List<Enrollment> enrollments;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    private List<Review> reviews;


}

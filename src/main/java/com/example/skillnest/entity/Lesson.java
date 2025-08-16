package com.example.skillnest.entity;

import java.util.Set;
import java.util.UUID;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "lessons")
@Getter
public class Lesson {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Setter
    @Column(nullable = false, length = 200)
    private String title;

    @Setter
    @Column(columnDefinition = "TEXT")
    private String content;

    @ManyToOne
    @JoinColumn(name = "section_id")
    private CourseSection section;

    @OneToMany(mappedBy = "lesson", cascade = CascadeType.ALL)
    private Set<LessonProgress> progresses;
}

package com.example.skillnest.entity;

import java.util.Set;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "lessons")
@Getter
@Setter
public class Lesson {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, length = 200)
    private String title;

    @Column(nullable = false, name = "lesson_order")
    private Integer order;

    @Column(name = "video_url")
    private String videoUrl;

    @Column(columnDefinition = "TEXT")
    private String content;

    @ManyToOne
    @JoinColumn(name = "section_id")
    @JsonBackReference
    private Section section;

    @OneToMany(mappedBy = "lesson", cascade = CascadeType.ALL)
    private Set<LessonProgress> progresses;
}

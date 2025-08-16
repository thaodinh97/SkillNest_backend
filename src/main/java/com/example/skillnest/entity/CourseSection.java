package com.example.skillnest.entity;

import java.util.UUID;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "course_sections")
@Getter
@Setter
public class CourseSection {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String title;

    @Column(name = "section_order")
    private Integer order;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;
}

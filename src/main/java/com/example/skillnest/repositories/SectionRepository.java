package com.example.skillnest.repositories;

import com.example.skillnest.entity.CourseSection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SectionRepository extends JpaRepository<CourseSection, UUID> {
}

package com.example.skillnest.repositories;

import com.example.skillnest.entity.Course;
import com.example.skillnest.entity.Section;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SectionRepository extends JpaRepository<Section, UUID> {
    List<Section> findByCourse(Course course);
}

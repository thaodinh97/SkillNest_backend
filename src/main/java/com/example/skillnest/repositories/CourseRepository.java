package com.example.skillnest.repositories;

import java.util.List;
import java.util.UUID;

import com.example.skillnest.dto.responses.CourseResponse;
import com.example.skillnest.dto.responses.CourseSummaryResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.skillnest.entity.Course;

@Repository
public interface CourseRepository extends JpaRepository<Course, UUID> {
    List<Course> findByInstructor_Id(UUID instructorId);

}

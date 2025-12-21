package com.example.skillnest.repositories;

import com.example.skillnest.dto.responses.EnrolledCourseResponse;
import com.example.skillnest.entity.Course;
import com.example.skillnest.entity.Enrollment;
import com.example.skillnest.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, UUID> {
    boolean existsByUserAndCourse(User user, Course course);

    List<Enrollment> findByUser(User user);
}

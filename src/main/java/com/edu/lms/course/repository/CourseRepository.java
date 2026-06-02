package com.edu.lms.course.repository;

import com.edu.lms.course.entity.Course;
import com.edu.lms.course.entity.CourseStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CourseRepository extends JpaRepository<Course, UUID> {

    List<Course> findByStatus(CourseStatus status);
}
package com.edu.lms.enrollment.repository;

import com.edu.lms.enrollment.entity.Enrollment;
import com.edu.lms.enrollment.entity.EnrollmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, UUID> {

    boolean existsByStudentIdAndCourseId(UUID studentId, UUID courseId);

    boolean existsByStudentIdAndCourseIdAndStatus(UUID id, UUID courseId, EnrollmentStatus enrollmentStatus);
}
package com.edu.lms.enrollment.entity;

import com.edu.lms.course.entity.Course;
import com.edu.lms.user.entity.User;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "enrollments")
 public class Enrollment {

    UUID id;

    @ManyToOne
    User student;
    @ManyToOne
    Course course;

    LocalDateTime enrolledAt;

    @Enumerated(EnumType.STRING)
    EnrollmentStatus status; // ACTIVE, EXPIRED, REFUNDED
}
package com.edu.lms.enrollment.controller;

import com.edu.lms.common.exception.BusinessException;
import com.edu.lms.common.exception.ResourceNotFoundException;
import com.edu.lms.common.response.ApiResponse;
import com.edu.lms.course.entity.Course;
import com.edu.lms.course.repository.CourseRepository;
import com.edu.lms.enrollment.entity.Enrollment;
import com.edu.lms.enrollment.entity.EnrollmentStatus;
import com.edu.lms.enrollment.repository.EnrollmentRepository;
import com.edu.lms.user.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/enrollments")
@RequiredArgsConstructor
@Tag(name = "Enrollment", description = "Course enrollment management")
public class EnrollmentController {

    private final EnrollmentRepository enrollmentRepository;
    private final CourseRepository courseRepository;

    // ===== POST /api/v1/enrollments/courses/{courseId} =====

    @PostMapping("/courses/{courseId}")
    @Operation(summary = "Enroll the current user in a course")
    public ResponseEntity<ApiResponse<Void>> enroll(
            @PathVariable UUID courseId,
            @AuthenticationPrincipal User currentUser) {

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found"));

        boolean alreadyEnrolled = enrollmentRepository
                .existsByStudentIdAndCourseIdAndStatus(
                        currentUser.getId(), courseId, EnrollmentStatus.ACTIVE);

        if (alreadyEnrolled) {
            throw new BusinessException("You are already enrolled in this course");
        }

        enrollmentRepository.save(Enrollment.builder()
                .student(currentUser)
                .course(course)
                .status(EnrollmentStatus.ACTIVE)
                .build());

        return ResponseEntity.ok(ApiResponse.success("Enrolled successfully"));
    }

    // ===== GET /api/v1/enrollments/courses/{courseId}/status =====

    @GetMapping("/courses/{courseId}/status")
    @Operation(summary = "Check if the current user is enrolled")
    public ResponseEntity<ApiResponse<EnrollmentStatusDto>> checkStatus(
            @PathVariable UUID courseId,
            @AuthenticationPrincipal User currentUser) {

        if (currentUser == null) {
            return ResponseEntity.ok(
                    ApiResponse.success("Status checked", new EnrollmentStatusDto(false)));
        }

        boolean enrolled = enrollmentRepository.existsByStudentIdAndCourseIdAndStatus(
                currentUser.getId(), courseId, EnrollmentStatus.ACTIVE);

        return ResponseEntity.ok(
                ApiResponse.success("Status checked", new EnrollmentStatusDto(enrolled)));
    }

    public record EnrollmentStatusDto(boolean enrolled) {}
}
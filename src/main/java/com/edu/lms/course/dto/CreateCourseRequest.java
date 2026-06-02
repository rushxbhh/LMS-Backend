package com.edu.lms.course.dto;

import com.edu.lms.course.entity.CourseLevel;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class CreateCourseRequest {

    @NotBlank
    private String title;

    private String description;

    private String thumbnailUrl;

    private BigDecimal price;

    private Boolean isFree;

    private CourseLevel level;

    private String category;

    private UUID teacherId;
}
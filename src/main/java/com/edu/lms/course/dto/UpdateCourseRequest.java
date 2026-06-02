package com.edu.lms.course.dto;

import com.edu.lms.course.entity.CourseLevel;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class UpdateCourseRequest {

    private String title;

    private String description;

    private String thumbnailUrl;

    private BigDecimal price;

    private Boolean isFree;

    private CourseLevel level;

    private String category;
}
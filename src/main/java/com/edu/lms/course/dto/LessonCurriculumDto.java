package com.edu.lms.course.dto;

import com.edu.lms.course.entity.LessonType;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class LessonCurriculumDto {

    private UUID id;
    private String title;
    private LessonType type;
    private Integer durationMinutes;
    private Integer orderIndex;
    private Boolean freePreview;
    private Boolean accessible;   // key flag for frontend
    private String videoUrl;      // null if not accessible
}

package com.edu.lms.course.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Builder
public class ModuleCurriculumDto {
    private UUID id;
    private String title;
    private Integer orderIndex;
    private List<LessonCurriculumDto> lessons;
}

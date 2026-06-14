package com.edu.lms.course.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CurriculumDto {
    private List<ModuleCurriculumDto> modules;
}

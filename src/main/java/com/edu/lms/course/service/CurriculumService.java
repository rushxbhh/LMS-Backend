package com.edu.lms.course.service;

import com.edu.lms.course.dto.CurriculumDto;
import java.util.UUID;

public interface CurriculumService {
    CurriculumDto getCurriculum(UUID courseId);
}

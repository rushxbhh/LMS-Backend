package com.edu.lms.course.service;

import com.edu.lms.course.dto.CreateLessonRequest;
import com.edu.lms.course.dto.LessonDto;
import com.edu.lms.course.dto.UpdateLessonRequest;

import java.util.UUID;

public interface LessonService {

    LessonDto createLesson(UUID moduleId,
                           CreateLessonRequest request);

    LessonDto updateLesson(UUID lessonId,
                           UpdateLessonRequest request);

    LessonDto getLesson(UUID lessonId);

    void deleteLesson(UUID lessonId);
}

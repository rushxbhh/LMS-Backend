package com.edu.lms.course.service;

import com.edu.lms.common.exception.ResourceNotFoundException;
import com.edu.lms.course.dto.CreateLessonRequest;
import com.edu.lms.course.dto.LessonDto;
import com.edu.lms.course.dto.UpdateLessonRequest;
import com.edu.lms.course.entity.Course;
import com.edu.lms.course.entity.CourseModule;
import com.edu.lms.course.entity.Lesson;
import com.edu.lms.course.repository.CourseRepository;
import com.edu.lms.course.repository.LessonRepository;
import com.edu.lms.course.repository.ModuleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LessonServiceImpl
        implements LessonService {

    private final LessonRepository lessonRepository;
    private final ModuleRepository moduleRepository;
    private final CourseRepository courseRepository;

    @Override
    public LessonDto createLesson(UUID moduleId,
                                  CreateLessonRequest request) {

        CourseModule module =
                moduleRepository.findById(moduleId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Module not found"));

        Lesson lesson =
                Lesson.builder()
                        .title(request.getTitle())
                        .type(request.getType())
                        .videoUrl(request.getVideoUrl())
                        .content(request.getContent())
                        .durationMinutes(
                                request.getDurationMinutes())
                        .orderIndex(
                                request.getOrderIndex())
                        .freePreview(
                                request.getFreePreview())
                        .module(module)
                        .build();

        lesson = lessonRepository.save(lesson);

        updateCourseStats(module.getCourse());

        return mapToDto(lesson);
    }

    @Override
    public LessonDto updateLesson(UUID lessonId,
                                  UpdateLessonRequest request) {

        Lesson lesson =
                lessonRepository.findById(lessonId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Lesson not found"));

        lesson.setTitle(request.getTitle());
        lesson.setType(request.getType());
        lesson.setVideoUrl(request.getVideoUrl());
        lesson.setContent(request.getContent());
        lesson.setDurationMinutes(
                request.getDurationMinutes());
        lesson.setOrderIndex(
                request.getOrderIndex());
        lesson.setFreePreview(
                request.getFreePreview());

        lesson = lessonRepository.save(lesson);

        updateCourseStats(
                lesson.getModule().getCourse());

        return mapToDto(lesson);
    }

    @Override
    public LessonDto getLesson(UUID lessonId) {

        Lesson lesson =
                lessonRepository.findById(lessonId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Lesson not found"));

        return mapToDto(lesson);
    }

    @Override
    public void deleteLesson(UUID lessonId) {

        Lesson lesson =
                lessonRepository.findById(lessonId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Lesson not found"));

        Course course =
                lesson.getModule().getCourse();

        lessonRepository.delete(lesson);

        updateCourseStats(course);
    }

    private void updateCourseStats(Course course) {

        int totalLessons = 0;
        int totalDuration = 0;

        for (CourseModule module :
                course.getModules()) {

            List<Lesson> lessons =
                    lessonRepository
                            .findByModuleId(
                                    module.getId());

            totalLessons += lessons.size();

            totalDuration += lessons.stream()
                    .mapToInt(l ->
                            l.getDurationMinutes() == null
                                    ? 0
                                    : l.getDurationMinutes())
                    .sum();
        }

        course.setTotalLessons(totalLessons);
        course.setTotalDurationMinutes(totalDuration);

        courseRepository.save(course);
    }

    private LessonDto mapToDto(
            Lesson lesson) {

        return LessonDto.builder()
                .id(lesson.getId())
                .title(lesson.getTitle())
                .type(lesson.getType())
                .videoUrl(lesson.getVideoUrl())
                .content(lesson.getContent())
                .durationMinutes(
                        lesson.getDurationMinutes())
                .orderIndex(
                        lesson.getOrderIndex())
                .freePreview(
                        lesson.getFreePreview())
                .build();
    }
}

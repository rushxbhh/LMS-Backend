package com.edu.lms.course.service;

import com.edu.lms.common.exception.ResourceNotFoundException;
import com.edu.lms.course.dto.CourseDto;
import com.edu.lms.course.dto.CreateCourseRequest;
import com.edu.lms.course.dto.UpdateCourseRequest;
import com.edu.lms.course.entity.Course;
import com.edu.lms.course.entity.CourseStatus;
import com.edu.lms.course.repository.CourseRepository;
import com.edu.lms.user.entity.User;
import com.edu.lms.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final UserRepository userRepository;

    @Override
    public CourseDto createCourse(CreateCourseRequest request) {

        User teacher = userRepository.findById(request.getTeacherId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Teacher not found"));

        Course course = Course.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .thumbnailUrl(request.getThumbnailUrl())
                .price(request.getPrice())
                .isFree(request.getIsFree())
                .level(request.getLevel())
                .category(request.getCategory())
                .status(CourseStatus.DRAFT)
                .teacher(teacher)
                .build();

        return mapToDto(courseRepository.save(course));
    }

    @Override
    public List<CourseDto> getAllPublishedCourses() {

        return courseRepository.findByStatus(
                        CourseStatus.PUBLISHED)
                .stream()
                .map(this::mapToDto)
                .toList();
    }

    @Override
    public CourseDto getCourseById(UUID id) {

        Course course = courseRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Course not found"));

        return mapToDto(course);
    }

    @Override
    public CourseDto updateCourse(UUID id,
                                  UpdateCourseRequest request) {

        Course course = courseRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Course not found"));

        course.setTitle(request.getTitle());
        course.setDescription(request.getDescription());
        course.setThumbnailUrl(request.getThumbnailUrl());
        course.setPrice(request.getPrice());
        course.setIsFree(request.getIsFree());
        course.setCategory(request.getCategory());
        course.setLevel(request.getLevel());

        return mapToDto(courseRepository.save(course));
    }

    @Override
    public void deleteCourse(UUID id) {

        Course course = courseRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Course not found"));

        courseRepository.delete(course);
    }

    @Override
    public CourseDto publishCourse(UUID id) {

        Course course = courseRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Course not found"));

        course.setStatus(CourseStatus.PUBLISHED);

        return mapToDto(courseRepository.save(course));
    }

    private CourseDto mapToDto(Course course) {

        return CourseDto.builder()
                .id(course.getId())
                .title(course.getTitle())
                .description(course.getDescription())
                .thumbnailUrl(course.getThumbnailUrl())
                .price(course.getPrice())
                .isFree(course.getIsFree())
                .level(course.getLevel())
                .category(course.getCategory())
                .status(course.getStatus())
                .totalLessons(course.getTotalLessons())
                .totalDurationMinutes(course.getTotalDurationMinutes())
                .build();
    }
}

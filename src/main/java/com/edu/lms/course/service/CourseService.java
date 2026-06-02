package com.edu.lms.course.service;
import com.edu.lms.course.dto.CourseDto;
import com.edu.lms.course.dto.CreateCourseRequest;
import com.edu.lms.course.dto.UpdateCourseRequest;

import java.util.List;
import java.util.UUID;

public interface CourseService {

    CourseDto createCourse(CreateCourseRequest request);

    List<CourseDto> getAllPublishedCourses();

    CourseDto getCourseById(UUID id);

    CourseDto updateCourse(UUID id,
                           UpdateCourseRequest request);

    void deleteCourse(UUID id);

    CourseDto publishCourse(UUID id);
}
package com.edu.lms.course.dto;

import lombok.Data;

@Data
public class CreateModuleRequest {

    private String title;

    private Integer orderIndex;
}
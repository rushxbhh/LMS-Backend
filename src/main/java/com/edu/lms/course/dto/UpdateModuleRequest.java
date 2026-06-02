package com.edu.lms.course.dto;

import lombok.Data;

@Data
public class UpdateModuleRequest {

    private String title;

    private Integer orderIndex;
}
package com.edu.lms.course.service;

import com.edu.lms.course.dto.CreateModuleRequest;
import com.edu.lms.course.dto.ModuleDto;
import com.edu.lms.course.dto.UpdateModuleRequest;

import java.util.UUID;

public interface ModuleService {

    ModuleDto createModule(UUID courseId,
                           CreateModuleRequest request);

    ModuleDto updateModule(UUID moduleId,
                           UpdateModuleRequest request);

    void deleteModule(UUID moduleId);

}

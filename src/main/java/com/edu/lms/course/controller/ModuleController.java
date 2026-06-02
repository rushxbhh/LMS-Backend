package com.edu.lms.course.controller;

import com.edu.lms.common.response.ApiResponse;
import com.edu.lms.course.dto.CreateModuleRequest;
import com.edu.lms.course.dto.ModuleDto;
import com.edu.lms.course.dto.UpdateModuleRequest;
import com.edu.lms.course.service.ModuleService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/courses")
@RequiredArgsConstructor
public class ModuleController {

    private final ModuleService moduleService;

    @PostMapping("/{courseId}/modules")
    public ApiResponse<ModuleDto> createModule(
            @PathVariable UUID courseId,
            @RequestBody CreateModuleRequest request) {

        return ApiResponse.success(
                "Module created",
                moduleService.createModule(
                        courseId,
                        request));
    }

    @PutMapping("/{courseId}/modules/{moduleId}")
    public ApiResponse<ModuleDto> updateModule(
            @PathVariable UUID moduleId,
            @RequestBody UpdateModuleRequest request) {

        return ApiResponse.success(
                "Module updated",
                moduleService.updateModule(
                        moduleId,
                        request));
    }

    @DeleteMapping("/{courseId}/modules/{moduleId}")
    public ApiResponse<String> deleteModule(
            @PathVariable UUID moduleId) {

        moduleService.deleteModule(moduleId);

        return ApiResponse.success(
                "Module deleted",
                "SUCCESS");
    }
}

package com.apptomio.courseservice.service;

import com.apptomio.courseservice.model.dto.TeacherDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "TEACHER-SERVICE")
public interface TeacherServiceClient {

    @GetMapping("/teacher/{id}")
    TeacherDto getById(@PathVariable String id);

    @PostMapping("/teacher/enroll/{teacherId}/course/{courseName}")
    void courseEnrollment(@PathVariable String teacherId, @PathVariable String courseName);
}

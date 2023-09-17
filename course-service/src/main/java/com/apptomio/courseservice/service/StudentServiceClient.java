package com.apptomio.courseservice.service;

import com.apptomio.courseservice.model.dto.StudentDto;

import org.apache.catalina.LifecycleState;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "STUDENT-SERVICE")
public interface StudentServiceClient {


    @GetMapping("/student/{id}")
    StudentDto getStudentById(@PathVariable String id);

    @PostMapping("/student/enroll/{studentId}/course/{courseName}")
    void courseEnrollment(@PathVariable String studentId, @PathVariable String courseName);

    @PostMapping("student/emails")
    List<StudentDto>  getStudentsByEmails(@RequestBody List<String> emails);
}

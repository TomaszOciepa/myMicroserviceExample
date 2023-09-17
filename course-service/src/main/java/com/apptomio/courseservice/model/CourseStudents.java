package com.apptomio.courseservice.model;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CourseStudents {

    private LocalDateTime enrollmentData;
    private String studentEmail;

    public CourseStudents(@NotNull String studentEmail) {
        this.enrollmentData = LocalDateTime.now();
        this.studentEmail = studentEmail;
    }


}

package com.apptomio.courseservice.model;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CourseTeachers {

    private LocalDateTime enrollmentData;
    private String teacherId;
    private String teacherEmail;

    public CourseTeachers(@NotNull String teacherId, @NotNull String teacherEmail) {
        this.enrollmentData = LocalDateTime.now();
        this.teacherId = teacherId;
        this.teacherEmail = teacherEmail;
    }
}

package com.apptomio.studentservice.model;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class MyCourse {


    private LocalDateTime enrollmentDate;
    private String courseName;

    public MyCourse(@NotNull String courseName) {
        this.enrollmentDate = LocalDateTime.now();
        this.courseName = courseName;
    }
}

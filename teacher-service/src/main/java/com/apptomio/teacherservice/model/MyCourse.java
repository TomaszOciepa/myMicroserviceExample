package com.apptomio.teacherservice.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class MyCourse {

    private String courseName;
    private LocalDateTime enrollmentDate;

    public MyCourse(String courseName) {
        this.courseName = courseName;
        this.enrollmentDate = LocalDateTime.now();
    }
}

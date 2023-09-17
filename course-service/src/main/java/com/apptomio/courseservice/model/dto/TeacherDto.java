package com.apptomio.courseservice.model.dto;

import com.apptomio.courseservice.model.Status;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TeacherDto {

    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private Status status;

    public TeacherDto(String firstName, String lastName, String email, Status status) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.status = status;
    }
}

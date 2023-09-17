package com.apptomio.courseservice.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Document
@Getter
@Setter
public class Course {

    @Id
    private String id;
    private String name;
    private Status status;
    private Long participantsLimit;
    private Long participantsNumber;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private List<CourseStudents> courseStudents = new ArrayList<>();
    private List<CourseTeachers> courseTeachers = new ArrayList<>();

    public void incrementParticipantsNumber(){
        participantsNumber++;
        if(getParticipantsNumber().equals(getParticipantsLimit())){
            setStatus(Status.FULL);
        }
    }
}

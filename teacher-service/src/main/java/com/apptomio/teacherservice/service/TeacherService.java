package com.apptomio.teacherservice.service;

import com.apptomio.teacherservice.model.Status;
import com.apptomio.teacherservice.model.Teacher;

import java.util.List;

public interface TeacherService {

    List<Teacher> getAll(Status status);
    Teacher getById(String id);
    Teacher add(Teacher teacher);
    Teacher put(String id, Teacher teacher);
    Teacher patch(String id, Teacher teacher);
    void delete(String id);

    void courseEnrollment(String teacherId, String courseName);
}

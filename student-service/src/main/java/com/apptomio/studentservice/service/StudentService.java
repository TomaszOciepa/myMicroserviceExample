package com.apptomio.studentservice.service;

import com.apptomio.studentservice.model.Status;
import com.apptomio.studentservice.model.Student;

import java.time.LocalDateTime;
import java.util.List;

public interface StudentService {

    List<Student> getAllStudents(Status status);
    Student getStudentById(String id);
    Student getStudentByEmail(String email);
    Student addStudent(Student student);
    Student putStudent(String id, Student student);
    Student patchStudent(String id, Student student);
    void deleteStudent(String id);

    void courseEnrollment(String studentId, String courseName);

    List<Student> getStudentsByEmails(List<String> emails);

}

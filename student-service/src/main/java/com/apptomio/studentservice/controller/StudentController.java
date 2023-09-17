package com.apptomio.studentservice.controller;

import com.apptomio.studentservice.model.Status;
import com.apptomio.studentservice.model.Student;
import com.apptomio.studentservice.service.StudentService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/student")
public class StudentController {

    private final StudentService studentService;

    @GetMapping
    public List<Student> getAllStudents(@RequestParam(required = false)Status status){
        return studentService.getAllStudents(status);
    }

    @GetMapping("/{id}")
    public Student getStudentById(@PathVariable String id){
        return studentService.getStudentById(id);
    }

    @GetMapping("/email")
    public Student getStudentByEmail(@RequestParam String email){
        return studentService.getStudentByEmail(email);
    }

    @PostMapping
    public Student addStudent(@RequestBody Student student){
        return  studentService.addStudent(student);
    }

    @PostMapping("/emails")
    public List<Student> getStudentsByEmails(@RequestBody List<String> emails ){
        return studentService.getStudentsByEmails(emails);
    }

    @PutMapping("/{id}")
    public Student putStudent(@PathVariable String id, @RequestBody Student student){
        return studentService.putStudent(id,student);
    }

    @PatchMapping("/{id}")
    public Student patchStudent(@PathVariable String id, @RequestBody Student student){
        return studentService.patchStudent(id, student);
    }

    @DeleteMapping("/{id}")
    public void deleteStudent(@PathVariable String id){
        studentService.deleteStudent(id);
    }

    @PostMapping("/enroll/{studentId}/course/{courseName}")
    public void courseEnrollment(@PathVariable String studentId, @PathVariable String courseName){
        studentService.courseEnrollment(studentId, courseName);
    }
}

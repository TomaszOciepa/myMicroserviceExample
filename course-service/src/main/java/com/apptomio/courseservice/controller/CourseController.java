package com.apptomio.courseservice.controller;

import com.apptomio.courseservice.model.Course;
import com.apptomio.courseservice.model.Status;
import com.apptomio.courseservice.model.dto.StudentDto;
import com.apptomio.courseservice.service.CourseService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/course")
@AllArgsConstructor
public class CourseController {

    private final CourseService courseService;


    @GetMapping
    public List<Course> getAllCourses(@RequestParam(required = false) Status status) {
        return courseService.getAllCourses(status);
    }

    @GetMapping("/{id}")
    public Course getCourseById(@PathVariable String id) {
        return courseService.getCourseById(id);
    }

    @PostMapping
    public Course addCourse(@RequestBody Course course) {
        return courseService.addCourse(course);
    }

    @PutMapping
    public Course putCourse(@PathVariable String id, @RequestBody Course course) {
        return courseService.putCourse(id, course);
    }

    @PatchMapping
    public Course patchCourse(@PathVariable String id, @RequestBody Course course) {
        return courseService.patchCourse(id, course);
    }

    @DeleteMapping("/{id}")
    public void deleteCourse(@PathVariable String id) {
        courseService.deleteCourse(id);
    }

    @PostMapping("/{courseId}/student/{studentId}")
    public ResponseEntity<?> studentCourseEnrollment(@PathVariable String courseId, @PathVariable String studentId) {
        courseService.studentCourseEnrollment(courseId, studentId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{courseId}/teacher/{teacherId}")
    public ResponseEntity<?> teacherCourseEnrollment(@PathVariable String courseId, @PathVariable String teacherId){
        courseService.teacherCourseEnrollment(courseId, teacherId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/courses/{courseId}/student-members")
    public List<StudentDto> getStudentMembers(@PathVariable String courseId){
        return courseService.getStudentMembers(courseId);
    }
}

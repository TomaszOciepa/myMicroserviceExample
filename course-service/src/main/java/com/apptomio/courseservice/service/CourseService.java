package com.apptomio.courseservice.service;

import com.apptomio.courseservice.model.Course;
import com.apptomio.courseservice.model.Status;
import com.apptomio.courseservice.model.dto.StudentDto;

import java.util.List;

public interface CourseService {

    List<Course> getAllCourses(Status status);
    Course getCourseById(String id);
    Course addCourse(Course course);
    Course putCourse(String id, Course course);
    Course patchCourse(String id, Course course);
    void deleteCourse(String id);
    void studentCourseEnrollment(String courseId, String studentId);
    void teacherCourseEnrollment(String courseId, String teacherId);

    List<StudentDto> getStudentMembers(String courseId);

}


package com.apptomio.courseservice.service;

import com.apptomio.courseservice.exception.CourseError;
import com.apptomio.courseservice.exception.CourseException;
import com.apptomio.courseservice.model.Course;
import com.apptomio.courseservice.model.CourseStudents;
import com.apptomio.courseservice.model.CourseTeachers;
import com.apptomio.courseservice.model.Status;
import com.apptomio.courseservice.model.dto.StudentDto;
import com.apptomio.courseservice.model.dto.TeacherDto;
import com.apptomio.courseservice.repo.CourseRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final StudentServiceClient studentServiceClient;
    private final TeacherServiceClient teacherServiceClient;

    @Override
    public List<Course> getAllCourses(Status status) {

        if (status != null) {
            return courseRepository.findAllByStatus(status);
        }
        return courseRepository.findAll();

    }

    @Override
    public Course getCourseById(String id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new CourseException(CourseError.COURSE_NOT_FOUND));
    }

    @Override
    public Course addCourse(Course course) {


        if (courseRepository.existsByName(course.getName())) {
            throw new CourseException(CourseError.COURSE_NAME_ALREADY_EXISTS);
        }

        return courseRepository.save(course);
    }

    @Override
    public Course putCourse(String id, Course course) {

        return courseRepository.findById(id)
                .map(courseFromDb -> {
                    if (!courseFromDb.getName().equals(course.getName())
                            && courseRepository.existsByName(course.getName())) {
                        throw new CourseException(CourseError.COURSE_NAME_ALREADY_EXISTS);
                    }
                    courseFromDb.setName(course.getName());
                    courseFromDb.setStatus(course.getStatus());
                    courseFromDb.setStartDate(course.getStartDate());
                    courseFromDb.setEndDate(course.getEndDate());
                    courseFromDb.setParticipantsLimit(course.getParticipantsLimit());
                    courseFromDb.setParticipantsNumber(course.getParticipantsNumber());
                    return courseRepository.save(courseFromDb);
                }).orElseThrow(() -> new CourseException(CourseError.COURSE_NOT_FOUND));


    }

    @Override
    public Course patchCourse(String id, Course course) {
        return courseRepository.findById(id)
                .map((courseFromDb -> {
                    if (!course.getName().isEmpty()) {
                        courseFromDb.setName(course.getName());
                    }
                    if (course.getStartDate() != null) {
                        courseFromDb.setStartDate(course.getStartDate());
                    }
                    if (course.getEndDate() != null) {
                        courseFromDb.setEndDate(course.getEndDate());
                    }
                    if (course.getParticipantsLimit() != null) {
                        courseFromDb.setParticipantsLimit(course.getParticipantsLimit());
                    }
                    if (course.getParticipantsNumber() != null) {
                        courseFromDb.setParticipantsNumber(course.getParticipantsNumber());
                    }
                    if (course.getStatus() != null) {
                        courseFromDb.setStatus(course.getStatus());
                    }
                    return courseRepository.save(courseFromDb);
                })).orElseThrow(() -> new CourseException(CourseError.COURSE_NOT_FOUND));
    }

    @Override
    public void deleteCourse(String id) {
        courseRepository.findById(id)
                .orElseThrow(() -> new CourseException(CourseError.COURSE_NOT_FOUND));
        courseRepository.deleteById(id);
    }

    @Override
    public void studentCourseEnrollment(String courseId, String studentId) {
        Course course = getCourseById(courseId);
        validateCourseStatus(course);
        StudentDto studentDto = studentServiceClient.getStudentById(studentId);
        validateStudentBeforeCourseEnrolment(course, studentDto);
        course.incrementParticipantsNumber();
        CourseStudents courseStudents = new CourseStudents(studentDto.getEmail());
        studentServiceClient.courseEnrollment(studentId, course.getName());
        course.getCourseStudents().add(courseStudents);
        courseRepository.save(course);
    }

    @Override
    public void teacherCourseEnrollment(String courseId, String teacherId) {
        Course course = getCourseById(courseId);
        validateCourseStatus(course);
        TeacherDto teacherDto = teacherServiceClient.getById(teacherId);
        validateTeacherBeforeCourseEnrolment(course, teacherDto);
        CourseTeachers courseTeachers = new CourseTeachers(teacherDto.getId(), teacherDto.getEmail());
        teacherServiceClient.courseEnrollment(teacherId, course.getName());
        course.getCourseTeachers().add(courseTeachers);
        courseRepository.save(course);
    }

    private void validateStudentBeforeCourseEnrolment(Course course, StudentDto studentDto) {
        if (!Status.ACTIVE.equals(studentDto.getStatus())) {
            throw new CourseException(CourseError.STUDENT_IS_NOT_ACTIVE);
        }
        if (course.getCourseStudents()
                .stream()
                .anyMatch(member -> studentDto.getEmail().equals(member.getStudentEmail()))) {
            throw new CourseException(CourseError.STUDENT_ALREADY_ENROLLED);
        }
    }

    private void validateTeacherBeforeCourseEnrolment(Course course, TeacherDto teacherDto) {
        if (!Status.ACTIVE.equals(teacherDto.getStatus())) {
            throw new CourseException(CourseError.TEACHER_IS_NOT_ACTIVE);
        }
        if (course.getCourseTeachers()
                .stream()
                .anyMatch(member -> teacherDto.getEmail().equals(member.getTeacherEmail()))) {
            throw new CourseException(CourseError.TEACHER_ALREADY_ENROLLED);
        }
    }

    private void validateCourseStatus(Course course) {
        if (!Status.ACTIVE.equals(course.getStatus())) {
            throw new CourseException(CourseError.COURSE_IS_NOT_ACTIVE);
        }
    }

    @Override
    public List<StudentDto> getStudentMembers(String courseId) {
        Course course = getCourseById(courseId);
        List<String> courseStudentMembersEmails = getCourseStudentMembersEmails(course);
        return studentServiceClient.getStudentsByEmails(courseStudentMembersEmails);
    }

    private List<String> getCourseStudentMembersEmails(Course course) {
        List<String> studentMembers = course.getCourseStudents()
                .stream()
                .map(CourseStudents::getStudentEmail).collect(Collectors.toList());
        return studentMembers;
    }
}

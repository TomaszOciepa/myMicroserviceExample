package com.apptomio.studentservice.service;

import com.apptomio.studentservice.exception.StudentError;
import com.apptomio.studentservice.exception.StudentException;
import com.apptomio.studentservice.model.MyCourse;
import com.apptomio.studentservice.model.Status;
import com.apptomio.studentservice.model.Student;
import com.apptomio.studentservice.repo.StudentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    @Override
    public List<Student> getAllStudents(Status status) {
        if (status != null) {
            return studentRepository.findAllByStatus(status);
        }
        return studentRepository.findAll();
    }

    @Override
    public Student getStudentById(String id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new StudentException(StudentError.STUDENT_NOT_FOUND));
        if (!Status.ACTIVE.equals(student.getStatus())) {
            throw new StudentException(StudentError.STUDENT_IS_NOT_ACTIVE);
        }
        return student;
    }

    @Override
    public Student getStudentByEmail(String email) {
        Student student = studentRepository.findByEmail(email)
                .orElseThrow(() -> new StudentException(StudentError.STUDENT_NOT_FOUND));
        if (!Status.ACTIVE.equals(student.getStatus())) {
            throw new StudentException(StudentError.STUDENT_IS_NOT_ACTIVE);
        }
        return student;
    }

    @Override
    public Student addStudent(Student student) {
        validateStudentEmailExists(student.getEmail());
        student.setStatus(Status.ACTIVE);
        return studentRepository.save(student);
    }

    @Override
    public Student putStudent(String id, Student student) {
        return studentRepository.findById(id)
                .map(studentFromDb -> {
                    if (!studentFromDb.getEmail().equals(student.getEmail())
                            && studentRepository.existsByEmail(student.getEmail())) {
                        throw new StudentException(StudentError.STUDENT_EMAIL_ALREADY_EXISTS);
                    }
                    studentFromDb.setFirstName(student.getFirstName());
                    studentFromDb.setLastName(student.getLastName());
                    studentFromDb.setPhone(student.getPhone());
                    studentFromDb.setStatus(student.getStatus());
                    studentFromDb.setBirthDate(student.getBirthDate());
                    return studentRepository.save(studentFromDb);
                }).orElseThrow(() -> new StudentException(StudentError.STUDENT_NOT_FOUND));


    }

    @Override
    public Student patchStudent(String id, Student student) {
        Student studentFromDb = studentRepository.findById(id)
                .orElseThrow(() -> new StudentException(StudentError.STUDENT_NOT_FOUND));

        if (student.getFirstName() != null) {
            studentFromDb.setFirstName(student.getFirstName());
        }
        if (student.getLastName() != null) {
            studentFromDb.setLastName(student.getLastName());
        }
        if (student.getPhone() != null) {
            studentFromDb.setPhone(student.getPhone());
        }
        if (student.getBirthDate() != null) {
            studentFromDb.setBirthDate(student.getBirthDate());
        }
        if (student.getStatus() != null) {
            studentFromDb.setStatus(student.getStatus());
        }
        return studentRepository.save(studentFromDb);
    }

    @Override
    public void deleteStudent(String id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new StudentException(StudentError.STUDENT_NOT_FOUND));
        student.setStatus(Status.INACTIVE);
        studentRepository.save(student);
    }

    private void validateStudentEmailExists(String email) {
        if (studentRepository.existsByEmail(email)) {
            throw new StudentException(StudentError.STUDENT_EMAIL_ALREADY_EXISTS);
        }
    }

    @Override
    public void courseEnrollment(String studentId, String courseName) {
        Student studentFromDb = getStudentById(studentId);
        studentFromDb.getMyCourseList().add(new MyCourse(courseName));
        studentRepository.save(studentFromDb);
    }

    @Override
    public List<Student> getStudentsByEmails(List<String> emails) {
        return studentRepository.findAllByEmailIn(emails);
    }
}

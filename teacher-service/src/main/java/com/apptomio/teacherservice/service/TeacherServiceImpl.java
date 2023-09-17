package com.apptomio.teacherservice.service;

import com.apptomio.teacherservice.exception.TeacherError;
import com.apptomio.teacherservice.exception.TeacherException;
import com.apptomio.teacherservice.model.MyCourse;
import com.apptomio.teacherservice.model.Status;
import com.apptomio.teacherservice.model.Teacher;
import com.apptomio.teacherservice.repo.TeacherRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TeacherServiceImpl implements TeacherService {

    private final TeacherRepository teacherRepository;

    @Override
    public List<Teacher> getAll(Status status) {

        if (status != null) {
            teacherRepository.findAllByStatus(status);
        }

        return teacherRepository.findAll();
    }

    @Override
    public Teacher getById(String id) {

        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(() -> new TeacherException(TeacherError.TEACHER_NOT_FOUND));
        if (!Status.ACTIVE.equals(teacher.getStatus())) {
            throw new TeacherException(TeacherError.TEACHER_STATUS_IS_NOT_ACTIVE);
        }

        return teacher;
    }

    @Override
    public Teacher add(Teacher teacher) {
        validateTeacherEmailExists(teacher);
        teacher.setStatus(Status.ACTIVE);
        return teacherRepository.save(teacher);
    }

    @Override
    public Teacher put(String id, Teacher teacher) {
        return teacherRepository.findById(id)
                .map(teacherFromDb -> {
                    if (!teacherFromDb.getEmail().equals(teacher.getEmail())
                            && teacherRepository.existsByEmail(teacher.getEmail())) {
                        throw new TeacherException(TeacherError.TEACHER_EMAIL_ALREADY_EXISTS);
                    }
                    teacherFromDb.setFirstName(teacher.getFirstName());
                    teacherFromDb.setLastName(teacher.getLastName());
                    teacherFromDb.setBirthDate(teacher.getBirthDate());
                    teacherFromDb.setPhone(teacher.getPhone());
                    teacherFromDb.setStatus(teacher.getStatus());
                    return teacherRepository.save(teacherFromDb);
                }).orElseThrow(() -> new TeacherException(TeacherError.TEACHER_NOT_FOUND));
    }

    @Override
    public Teacher patch(String id, Teacher teacher) {
        Teacher teacherFromDb = teacherRepository.findById(id)
                .orElseThrow(() -> new TeacherException(TeacherError.TEACHER_NOT_FOUND));

        if (teacher.getFirstName() != null) {
            teacherFromDb.setFirstName(teacher.getFirstName());
        }
        if (teacher.getLastName() != null) {
            teacherFromDb.setLastName(teacher.getLastName());
        }
        if (teacher.getEmail() != null) {
            teacherFromDb.setEmail(teacher.getEmail());
        }
        if (teacher.getPhone() != null) {
            teacherFromDb.setPhone(teacher.getPhone());
        }
        if (teacher.getStatus() != null) {
            teacherFromDb.setStatus(teacher.getStatus());
        }
        if (teacher.getBirthDate() != null) {
            teacherFromDb.setBirthDate(teacher.getBirthDate());
        }

        return teacherRepository.save(teacherFromDb);
    }

    @Override
    public void delete(String id) {
        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(() -> new TeacherException(TeacherError.TEACHER_NOT_FOUND));

        teacher.setStatus(Status.INACTIVE);
        teacherRepository.save(teacher);
    }

    private void validateTeacherEmailExists(Teacher teacher) {
        if (teacherRepository.existsByEmail(teacher.getEmail())) {
            throw new TeacherException(TeacherError.TEACHER_EMAIL_ALREADY_EXISTS);
        }
    }

    @Override
    public void courseEnrollment(String teacherId, String courseName) {
        Teacher teacher = getById(teacherId);
        teacher.getMyCourseList().add(new MyCourse(courseName));
        teacherRepository.save(teacher);
    }
}

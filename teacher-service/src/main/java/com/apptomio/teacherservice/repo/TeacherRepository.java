package com.apptomio.teacherservice.repo;

import com.apptomio.teacherservice.model.Status;
import com.apptomio.teacherservice.model.Teacher;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeacherRepository extends MongoRepository<Teacher, String> {

    List<Teacher> findAllByStatus(Status status);
    boolean existsByEmail(String email);
}

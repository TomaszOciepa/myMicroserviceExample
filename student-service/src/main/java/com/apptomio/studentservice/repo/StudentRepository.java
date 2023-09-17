package com.apptomio.studentservice.repo;

import com.apptomio.studentservice.model.Status;
import com.apptomio.studentservice.model.Student;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends MongoRepository<Student, String> {
    List<Student> findAllByStatus(Status status);

    Optional<Student> findByEmail(String email);

    boolean existsByEmail(String email);

    List<Student> findAllByEmailIn(List<String> emails);
}

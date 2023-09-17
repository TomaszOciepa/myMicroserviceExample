package com.apptomio.courseservice.repo;

import com.apptomio.courseservice.model.Course;
import com.apptomio.courseservice.model.Status;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CourseRepository extends MongoRepository<Course, String> {


    List<Course> findAllByStatus(Status status);
    boolean existsByName(String name);
}

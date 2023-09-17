package com.apptomio.adminservice.repo;

import com.apptomio.adminservice.model.Admin;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface AdminRepository extends MongoRepository<Admin, String> {

    boolean existsByEmail(String email);

    Optional<Admin> findByEmail(String email);
}

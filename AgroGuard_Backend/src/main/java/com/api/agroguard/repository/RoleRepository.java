package com.api.agroguard.repository;

import com.api.agroguard.entity.ERole;
import com.api.agroguard.entity.Role;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface RoleRepository extends MongoRepository<Role, String> {
    Optional<Role> findByName(ERole name);
}

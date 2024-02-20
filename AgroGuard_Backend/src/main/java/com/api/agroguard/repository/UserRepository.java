package com.api.agroguard.repository;

import com.api.agroguard.model.UserDO;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<UserDO, String> {
    Optional<UserDO> findById(String id);
    UserDO findByEmail(String email);

}

package com.api.agroguard.repository;

import com.api.agroguard.model.UserActivityDO;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserActivityRepository extends MongoRepository<UserActivityDO, String> {
}

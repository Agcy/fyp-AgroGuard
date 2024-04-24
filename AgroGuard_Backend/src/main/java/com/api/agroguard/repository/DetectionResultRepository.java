package com.api.agroguard.repository;

import com.api.agroguard.model.DetectionResultDO;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface DetectionResultRepository extends MongoRepository<DetectionResultDO, String> {
//    DetectionResultDO findByUserId(String userId);
    List<DetectionResultDO> findByUserId(String userId);
}

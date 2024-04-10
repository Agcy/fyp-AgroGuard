package com.api.agroguard.repository;

import com.api.agroguard.model.DiseaseDO;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DiseaseRepository extends MongoRepository<DiseaseDO, String> {
    DiseaseDO findDiseaseById(String id);
}

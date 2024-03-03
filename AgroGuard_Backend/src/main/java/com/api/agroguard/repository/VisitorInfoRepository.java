package com.api.agroguard.repository;

import com.api.agroguard.model.VisitorInfoDO;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface VisitorInfoRepository extends MongoRepository<VisitorInfoDO, String> {
    Optional<VisitorInfoDO> findByIp(String ip);
}

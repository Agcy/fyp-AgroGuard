package com.api.agroguard.repository;

import com.api.agroguard.model.UserDO;
import com.mongodb.lang.NonNullApi;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<UserDO, String> {
//    Optional<UserDO> findById(String id);
    Optional<UserDO> findByEmail(String email);
    // 检查邮箱是否已存在
    boolean existsByEmail(String email);

}

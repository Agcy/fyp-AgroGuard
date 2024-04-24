package com.api.agroguard.repository;

import com.api.agroguard.model.PostDO;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends MongoRepository<PostDO, String> {
    List<PostDO> findByTitleContainingIgnoreCaseOrContentContainingIgnoreCaseOrUserIdContainingIgnoreCase(String title, String content, String userName);
    List<PostDO> findByUserId(String userId);
}

package com.api.agroguard.repository;

import com.api.agroguard.model.CommentDO;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CommentRepository extends MongoRepository<CommentDO, String> {
    List<CommentDO> findByPostId(String postId);
}

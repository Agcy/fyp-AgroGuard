package com.api.agroguard.repository;

import com.api.agroguard.model.PostDO;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface PostRepository extends MongoRepository<PostDO, String> {
    List<PostDO> findByTitleContainingIgnoreCaseOrContentContainingIgnoreCaseOrUserIdContainingIgnoreCase(String title, String content, String userName);

}

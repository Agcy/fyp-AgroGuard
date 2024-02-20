package com.api.agroguard.repository;

import com.api.agroguard.model.ImageDO;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ImageRepository extends MongoRepository<ImageDO, String> {
    // 这里可以根据需要添加自定义的查询方法，例如根据userId查找图像
    List<ImageDO> findByUserId(String userId);
}

package com.api.agroguard.service;

import com.api.agroguard.model.PostDO;

import java.util.List;

public interface PostService {
    PostDO createPost(PostDO post);
    PostDO updatePost(String id, PostDO post);
    void deletePost(String id);
    PostDO getPostById(String id);
    List<PostDO> getAllPosts();
    List<PostDO> searchPosts(String keyword);
}

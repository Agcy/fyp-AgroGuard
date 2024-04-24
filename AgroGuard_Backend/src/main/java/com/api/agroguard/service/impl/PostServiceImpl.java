package com.api.agroguard.service.impl;

//import com.api.agroguard.entity.Post;
import com.api.agroguard.model.PostDO;
import com.api.agroguard.repository.PostRepository;
import com.api.agroguard.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Override
    public PostDO createPost(PostDO post) {
        post.setCreatedAt(LocalDateTime.now());
        post.setUpdatedAt(LocalDateTime.now());
        return postRepository.save(post);
    }

    @Override
    public PostDO updatePost(String id, PostDO updatedPost) {
        PostDO post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found with id " + id));
        post.setTitle(updatedPost.getTitle());
        post.setContent(updatedPost.getContent());
        post.setImageId(updatedPost.getImageId());
        // post.setBase64Image(updatedPost.getBase64Image()); // 如果使用Base64编码的图片
        post.setUpdatedAt(LocalDateTime.now());
        return postRepository.save(post);
    }

    @Override
    public void deletePost(String id) {
        postRepository.deleteById(id);
    }

    @Override
    public PostDO getPostById(String id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found with id " + id));
    }

    @Override
    public List<PostDO> getAllPosts() {
        return postRepository.findAll();
    }

    @Override
    public List<PostDO> searchPosts(String keyword) {
        return postRepository.findByTitleContainingIgnoreCaseOrContentContainingIgnoreCaseOrUserIdContainingIgnoreCase(keyword, keyword, keyword);
    }

    @Override
    public List<PostDO> getAllPostsByUserId(String userId) {
        return postRepository.findByUserId(userId);
    }
}

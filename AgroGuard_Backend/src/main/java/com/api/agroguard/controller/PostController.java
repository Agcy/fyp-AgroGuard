package com.api.agroguard.controller;

import com.api.agroguard.entity.MessageResponse;
import com.api.agroguard.entity.PostRequest;
import com.api.agroguard.model.PostDO;
import com.api.agroguard.service.PostService;
import com.api.agroguard.service.impl.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {
    @Autowired
    private PostService postService;

    // 创建帖子
    @PostMapping("/addPost")
    public ResponseEntity<?> createPost(@RequestBody PostRequest post, @AuthenticationPrincipal UserDetailsImpl userDetails) {


        PostDO postDO = new PostDO();
        postDO.setUserId(userDetails.getId()); // 使用用户名作为userId
        postDO.setUserName(userDetails.getUsername());
        postDO.setUserAvatar(userDetails.getAvatarUrl());
        postDO.setTitle(post.getTitle());
        postDO.setContent(post.getContent());
        postDO.setBase64Image(post.getBase64Imgs());
        postDO.setCreatedAt(LocalDateTime.now());
        postDO.setUpdatedAt(LocalDateTime.now());

        postService.createPost(postDO);

        return ResponseEntity.ok(new MessageResponse("Post Uploaded"));
    }

    // 更新帖子
    @PutMapping("/{username}/{postId}")
    public ResponseEntity<?> updatePost(@PathVariable String username, @PathVariable String postId, @RequestBody PostRequest post, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        if (!username.equals(userDetails.getUsername())) {
            return ResponseEntity.badRequest().body(null); // 或者返回一个合适的错误响应
        }
        // 这里可以添加更多的权限检查，例如检查帖子是否属于该用户
        PostDO postDO = new PostDO();

        postDO.setUserId(username); // 使用用户名作为userId
        postDO.setUserName(userDetails.getUsername());
        postDO.setTitle(post.getTitle());
        postDO.setContent(post.getContent());
        postDO.setBase64Image(post.getBase64Imgs());
        postDO.setUpdatedAt(LocalDateTime.now());
        postService.updatePost(postId, postDO);

        return ResponseEntity.ok(new MessageResponse("Post Updated"));
    }

    // 删除帖子
    @DeleteMapping("/{username}/{postId}")
    public ResponseEntity<?> deletePost(@PathVariable String username, @PathVariable String postId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        if (!username.equals(userDetails.getUsername())) {
            return ResponseEntity.badRequest().build(); // 或者返回一个合适的错误响应
        }
        // 这里可以添加更多的权限检查，例如检查帖子是否属于该用户
        postService.deletePost(postId);
        return ResponseEntity.ok().build();
    }

    // 获取所有帖子
    @GetMapping("/getAll")
    public List<PostDO> getAllPost(){
        return postService.getAllPosts();
    }

    // 获取单个帖子
    @GetMapping("post/{postId}")
    public PostDO getSinglePost(@PathVariable String postId) {
        return postService.getPostById(postId);
    }

    // 获取用户的所有帖子
    @GetMapping("/{userId}/getAll")
    public List<PostDO> getAllPostByUser(@PathVariable String userId) {
        return postService.getAllPostsByUserId(userId);
    }
}

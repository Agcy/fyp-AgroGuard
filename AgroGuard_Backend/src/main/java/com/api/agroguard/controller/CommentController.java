package com.api.agroguard.controller;

import com.api.agroguard.entity.CommentRequest;
import com.api.agroguard.model.CommentDO;
import com.api.agroguard.service.CommentService;
import com.api.agroguard.service.impl.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/api/comments")
public class CommentController {
    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    // 添加新评论
    @PostMapping("/comments")
    public ResponseEntity<?> addComment(@RequestBody CommentRequest commentRequest, @AuthenticationPrincipal UserDetailsImpl currentUser) {

        // 创建并填充评论对象
        CommentDO comment = new CommentDO();
        comment.setPostId(commentRequest.getPostId()); // 假设CommentRequest中包含postId
        comment.setContent(commentRequest.getContent()); // 评论内容
        comment.setUserId(currentUser.getId()); // 设置评论用户的ID
        comment.setUserName(currentUser.getUsername()); // 设置评论用户的用户名
        comment.setUserAvatarUrl(currentUser.getAvatarUrl()); // 假设UserDetailsImpl中包含用户头像URL
        comment.setCreatedAt(LocalDateTime.now());

        // 调用Service层添加评论
        CommentDO savedComment = commentService.addComment(comment);

        // 返回响应
        return ResponseEntity.ok(savedComment);
    }

    // 根据帖子ID获取评论
    @GetMapping("/post/{postId}")
    public ResponseEntity<List<CommentDO>> getCommentsByPostId(@PathVariable String postId) {
        List<CommentDO> comments = commentService.getCommentsByPostId(postId);
        return ResponseEntity.ok(comments);
    }
}

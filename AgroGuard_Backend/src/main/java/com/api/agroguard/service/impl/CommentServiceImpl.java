package com.api.agroguard.service.impl;

import com.api.agroguard.model.CommentDO;
import com.api.agroguard.repository.CommentRepository;
import com.api.agroguard.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public CommentDO addComment(CommentDO comment) {
        // 这里可以添加一些业务逻辑，比如设置时间戳
        return commentRepository.save(comment);
    }

    @Override
    public List<CommentDO> getCommentsByPostId(String postId) {
        return commentRepository.findByPostId(postId);
    }
}

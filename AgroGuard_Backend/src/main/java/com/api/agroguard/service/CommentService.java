package com.api.agroguard.service;

import com.api.agroguard.model.CommentDO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CommentService {
    CommentDO addComment(CommentDO comment);

    List<CommentDO> getCommentsByPostId(String postId);
}

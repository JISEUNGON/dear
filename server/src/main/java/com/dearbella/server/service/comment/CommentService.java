package com.dearbella.server.service.comment;

import com.dearbella.server.domain.Comment;
import com.dearbella.server.domain.CommentLike;
import com.dearbella.server.dto.request.comment.CommentAddRequestDto;
import com.dearbella.server.dto.request.comment.CommentEditRequestDto;
import com.dearbella.server.dto.response.comment.CommentResponseDto;

import java.util.List;

public interface CommentService {
    public Comment addComment(CommentAddRequestDto reviewId);
    public List<CommentResponseDto> getAll(Long id);
    public void deleteComment(Long commentId);
    public Comment editComment(CommentEditRequestDto dto);
    public String likeComment(Long commentId);
}

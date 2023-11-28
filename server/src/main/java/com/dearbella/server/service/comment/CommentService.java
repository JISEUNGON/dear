package com.dearbella.server.service.comment;

import com.dearbella.server.domain.Comment;
import com.dearbella.server.domain.CommentLike;
import com.dearbella.server.domain.DoctorResponse;
import com.dearbella.server.dto.request.comment.CommentAddRequestDto;
import com.dearbella.server.dto.request.comment.CommentDoctorRequestDto;
import com.dearbella.server.dto.request.comment.CommentEditRequestDto;
import com.dearbella.server.dto.response.comment.CommentMemberResponseDto;
import com.dearbella.server.dto.response.comment.CommentResponseDto;

import java.util.List;

public interface CommentService {
    public CommentMemberResponseDto addComment(CommentAddRequestDto reviewId);
    public List<CommentResponseDto> getAll(Long id);
    public void deleteComment(Long commentId);
    public Comment editComment(CommentEditRequestDto dto);
    public String likeComment(Long commentId);
    public DoctorResponse addDoctorResponse(CommentDoctorRequestDto dto);
}

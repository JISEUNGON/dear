package com.dearbella.server.service.comment;

import com.dearbella.server.domain.Comment;
import com.dearbella.server.domain.CommentLike;
import com.dearbella.server.domain.DoctorResponse;
import com.dearbella.server.domain.Member;
import com.dearbella.server.dto.request.comment.CommentAddRequestDto;
import com.dearbella.server.dto.request.comment.CommentDoctorRequestDto;
import com.dearbella.server.dto.request.comment.CommentEditRequestDto;
import com.dearbella.server.dto.response.comment.CommentMemberResponseDto;
import com.dearbella.server.dto.response.comment.CommentResponseDto;
import com.dearbella.server.exception.comment.CommentIdNotFoundException;
import com.dearbella.server.exception.member.MemberIdNotFoundException;
import com.dearbella.server.repository.CommentLikeRepository;
import com.dearbella.server.repository.CommentRepository;
import com.dearbella.server.repository.DoctorResponseRepository;
import com.dearbella.server.repository.MemberRepository;
import com.dearbella.server.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;
    private final CommentLikeRepository commentLikeRepository;
    private final DoctorResponseRepository doctorResponseRepository;

    @Override
    @Transactional
    public CommentMemberResponseDto addComment(final CommentAddRequestDto dto) {
        String name = memberRepository.findById(JwtUtil.getMemberId()).orElseThrow(
                () -> new MemberIdNotFoundException(JwtUtil.getMemberId().toString())
        ).getNickname();

        final Comment save = commentRepository.save(
                Comment.builder()
                        .parentComment(dto.getParentId())
                        .id(dto.getPostId())
                        .content(dto.getContent())
                        .content(dto.getContent())
                        .memberId(JwtUtil.getMemberId())
                        .deleted(false)
                        .description("커뮤니티 댓글")
                        .build()
        );

        return CommentMemberResponseDto.builder()
                .commentId(save.getCommentId())
                .parentId(save.getParentComment())
                .content(save.getContent())
                .memberName(name)
                .id(save.getId())
                .build();
    }

    @Override
    @Transactional
    public List<CommentResponseDto> getAll(final Long id) {
        final List<Comment> allById = commentRepository.findAllByIdAndDeletedFalse(id);
        List<CommentResponseDto> list = new ArrayList<>();

        for (Comment comment: allById) {
            final Member member = memberRepository.findById(comment.getMemberId()).orElseThrow(
                    () -> new MemberIdNotFoundException(comment.getMemberId().toString())
            );

            list.add(
                    CommentResponseDto.builder()
                            .commentId(comment.getCommentId())
                            .memberId(comment.getMemberId())
                            .parentId(comment.getParentComment())
                            .updatedAt(comment.getUpdatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                            .memberImage(member.getProfileImg())
                            .memberName(member.getNickname())
                            .likeNum((long) commentLikeRepository.findByCommentId(comment.getCommentId()).size())
                            .build()
            );
        }

        return list;
    }

    @Override
    @Transactional
    public void deleteComment(final Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new CommentIdNotFoundException(commentId)
        );

        comment.setDeleted(true);

        commentRepository.save(comment);
    }

    @Override
    @Transactional
    public Comment editComment(final CommentEditRequestDto dto) {
        Comment comment = commentRepository.findById(dto.getCommentId()).orElseThrow(
                () -> new CommentIdNotFoundException(dto.getCommentId())
        );

        comment.setContent(dto.getContent());

        return commentRepository.save(comment);
    }

    @Override
    @Transactional
    public String likeComment(Long commentId) {
        Optional<CommentLike> byCommentIdAndMemberId = commentLikeRepository.findByCommentIdAndMemberId(commentId, JwtUtil.getMemberId());
        if(byCommentIdAndMemberId.isEmpty()) {
            commentLikeRepository.save(
                    CommentLike.builder()
                            .commentId(commentId)
                            .memberId(JwtUtil.getMemberId())
                            .build()
            );

            return "save";
        } else {
            commentLikeRepository.delete(byCommentIdAndMemberId.get());

            return "delete";
        }
    }

    @Override
    @Transactional
    public DoctorResponse addDoctorResponse(CommentDoctorRequestDto dto) {
        return doctorResponseRepository.save(
                DoctorResponse.builder()
                        .postId(dto.getPostId())
                        .adminId(JwtUtil.getMemberId())
                        .content(dto.getContent())
                        .build()
        );
    }
}

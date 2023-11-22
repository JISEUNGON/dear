package com.dearbella.server.repository;

import com.dearbella.server.domain.CommentLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {
    public List<CommentLike> findByCommentId(Long commentId);
    public Optional<CommentLike> findByCommentIdAndMemberId(Long commentId, Long memberId);
}

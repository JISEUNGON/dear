package com.dearbella.server.repository;

import com.dearbella.server.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    public List<Comment> findAllByIdAndDeletedFalse(Long id);
    public List<Comment> findByMemberIdAAndDeletedFalse(Long memberId);
}

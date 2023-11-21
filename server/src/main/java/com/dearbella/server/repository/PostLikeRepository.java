package com.dearbella.server.repository;

import com.dearbella.server.domain.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {
    public List<PostLike> findByPostId(Long postId);

    public Optional<PostLike> findByMemberId(Long memberId);

    public Optional<PostLike> findByPostIdAndMemberId(Long id, Long memberId);
}

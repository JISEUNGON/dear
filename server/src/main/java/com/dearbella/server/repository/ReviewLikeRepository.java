package com.dearbella.server.repository;

import com.dearbella.server.domain.ReviewLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReviewLikeRepository extends JpaRepository<ReviewLike, Long> {
    public List<ReviewLike> findByReviewId(Long id);

    public Optional<ReviewLike> findByReviewIdAndMemberId(Long reviewId, Long memberId);
}

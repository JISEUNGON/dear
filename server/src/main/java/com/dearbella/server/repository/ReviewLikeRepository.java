package com.dearbella.server.repository;

import com.dearbella.server.domain.ReviewLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewLikeRepository extends JpaRepository<ReviewLike, Long> {
    public List<ReviewLike> findByReviewId(Long id);
}

package com.dearbella.server.service.review;

import com.dearbella.server.domain.Review;
import com.dearbella.server.domain.ReviewLike;
import com.dearbella.server.dto.request.review.ReviewAddRequestDto;
import com.dearbella.server.dto.response.review.*;

import java.util.List;
import java.util.Set;

public interface ReviewService {
    public Review addReview(ReviewAddRequestDto dto, List<String> befores, List<String> afters);
    public Set<ReviewResponseDto> findByCategory(Long category);
    public Set<ReviewResponseDto> findByQuery(String query);
    public ReviewDetailResponseDto findById(Long id);
    public List<MyReviewResponseDto> findMyReviews();
    public String likeReview(Long reviewId);
    public List<ReviewAdminResponseDto> getReviews(Long page);
    public String deleteReview(Long reviewId);
}

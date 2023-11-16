package com.dearbella.server.service.review;

import com.dearbella.server.domain.Review;
import com.dearbella.server.dto.request.review.ReviewAddRequestDto;
import com.dearbella.server.dto.response.review.ReviewAddResponseDto;
import com.dearbella.server.dto.response.review.ReviewResponseDto;

import java.util.List;
import java.util.Set;

public interface ReviewService {
    public Review addReview(ReviewAddRequestDto dto, List<String> befores, List<String> afters);

    public Set<ReviewResponseDto> findByCategory(Long category);
}

package com.dearbella.server.service.review;

import com.dearbella.server.domain.Review;
import com.dearbella.server.dto.request.review.ReviewAddRequestDto;
import com.dearbella.server.dto.response.review.ReviewAddResponseDto;

import java.util.List;

public interface ReviewService {
    public Review addReview(ReviewAddRequestDto dto, List<String> befores, List<String> afters);
}

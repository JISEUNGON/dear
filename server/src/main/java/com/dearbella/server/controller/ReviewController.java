package com.dearbella.server.controller;

import com.dearbella.server.domain.Review;
import com.dearbella.server.domain.ReviewLike;
import com.dearbella.server.dto.request.review.ReviewAddRequestDto;
import com.dearbella.server.dto.response.review.MyReviewResponseDto;
import com.dearbella.server.service.review.ReviewService;
import com.dearbella.server.service.s3.S3UploadService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/review")
@RequiredArgsConstructor
@Slf4j
@Api(tags = {"후기 API"})
public class ReviewController {
    private final ReviewService reviewService;
    private final S3UploadService s3UploadService;

    @ApiOperation("후기 작성")
    @PostMapping(value = "/add", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @Transactional
    public ResponseEntity<Review> addReview(@ModelAttribute ReviewAddRequestDto dto) throws IOException {
        List<String> befores = new ArrayList<>();
        List<String> afters = new ArrayList<>();

        for(MultipartFile file: dto.getBefores()) {
            befores.add(
                    s3UploadService.upload(file, "dearbella/review/before", false)
            );
        }

        for(MultipartFile file: dto.getBefores()) {
            afters.add(
                    s3UploadService.upload(file, "dearbella/review/after", false)
            );
        }

        return ResponseEntity.ok(reviewService.addReview(dto, befores, afters));
    }

    @ApiOperation("내가 작성한 후기")
    @GetMapping("/my")
    public ResponseEntity<List<MyReviewResponseDto>> getMyReviews() {
        return ResponseEntity.ok(reviewService.findMyReviews());
    }

    @ApiOperation("리뷰 좋아요/취소")
    @GetMapping("/like")
    public ResponseEntity<String> likeReview(@RequestParam Long reviewId) {
        return ResponseEntity.ok(reviewService.likeReview(reviewId));
    }
}

package com.dearbella.server.dto.response.review;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class MyReviewResponseDto {
    private Long reviewId;
    private String title;
    private Float rate;
}

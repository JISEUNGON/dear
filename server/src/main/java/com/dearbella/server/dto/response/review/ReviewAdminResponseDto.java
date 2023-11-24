package com.dearbella.server.dto.response.review;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class ReviewAdminResponseDto {
    private Long reviewId;
    private String reviewTittle;
    private Long totalPage;
}

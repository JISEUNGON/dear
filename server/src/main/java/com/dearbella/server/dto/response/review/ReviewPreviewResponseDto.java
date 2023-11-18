package com.dearbella.server.dto.response.review;

import com.dearbella.server.domain.Image;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class ReviewPreviewResponseDto {
    private String memberName;
    private String memberImage;
    private Long reviewId;
    private LocalDateTime updatedAt;
    private Image before;
    private Image after;
    private String title;
    private Boolean isLike;
    private String hospitalName;
    private Float rate;
    private Long likeNum;
    private Long commentNum;
}

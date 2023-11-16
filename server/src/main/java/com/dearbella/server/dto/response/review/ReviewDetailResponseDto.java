package com.dearbella.server.dto.response.review;

import com.dearbella.server.domain.Image;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class ReviewDetailResponseDto {
    private Long reviewId;
    private Long memberId;
    private LocalDateTime updatedAt;
    private String nickname;
    private String profileImg;
    private List<Image> befores;
    private List<Image> afters;
    private String title;
    private Float rate;
    private Long doctorId;
    private Long hospitalId;
    private String hospitalName;
    private Long likeNum;
    private Long commentNum;
    private String content;
}

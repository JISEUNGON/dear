package com.dearbella.server.dto.response.post;

import com.dearbella.server.domain.Image;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class PostDetailResponseDto {
    private Long postId;
    private String category;
    private Long memberId;
    private String memberImage;
    private String memberName;
    private String createdAt;
    private String content;
    private List<Image> images;
    private Long likeCount;
    private Long viewCount;
    private Long commentNum;
    private Boolean isLike;
}

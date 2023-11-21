package com.dearbella.server.dto.response.post;

import com.dearbella.server.domain.Image;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class PostFindResponseDto {
    private Long postId;
    private Long memberId;
    private String memberImage;
    private String memberName;
    private String createdAt;
    private String title;
    private String content;
    private List<Image> images;
    private Long viewNum;
    private Long likeNum;
    private Long commentNum;
}

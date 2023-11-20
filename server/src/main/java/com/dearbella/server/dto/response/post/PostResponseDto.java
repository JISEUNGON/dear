package com.dearbella.server.dto.response.post;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class PostResponseDto {
    private Long postId;
    private String memberImage;
    private String name;
    private String createdAt;
    private String content;
    private Long likeNum;
    private Long commentNum;
    private Long viewNum;
}

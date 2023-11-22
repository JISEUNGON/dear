package com.dearbella.server.dto.response.comment;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class CommentResponseDto {
    private Long commentId;
    private Long parentId;
    private String updatedAt;
    private Long memberId;
    private String memberName;
    private String memberImage;
    private Long likeNum;
}

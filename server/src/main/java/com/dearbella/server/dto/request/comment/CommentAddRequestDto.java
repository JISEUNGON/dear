package com.dearbella.server.dto.request.comment;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
public class CommentAddRequestDto {
    private Long postId;
    private Long parentId;
    private String content;
}

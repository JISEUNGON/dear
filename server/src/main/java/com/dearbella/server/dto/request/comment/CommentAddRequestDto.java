package com.dearbella.server.dto.request.comment;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
public class CommentAddRequestDto {
    private Long id;
    private Long parentId;
    private String content;
    private Boolean isReview;
}

package com.dearbella.server.dto.request.comment;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class CommentEditRequestDto {
    private Long commentId;
    private String content;
    private Long memberId;
}

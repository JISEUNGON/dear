package com.dearbella.server.dto.response.comment;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CommentMemberResponseDto {
    private Long commentId;
    private String content;
    private Long parentId;
    private String memberName;
    private Long id;
}

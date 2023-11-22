package com.dearbella.server.dto.request.comment;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class CommentDoctorRequestDto {
    private Long postId;
    private String content;
}

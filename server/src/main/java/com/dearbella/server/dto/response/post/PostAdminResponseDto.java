package com.dearbella.server.dto.response.post;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class PostAdminResponseDto {
    private Long postId;
    private String postName;
    private Long totalPage;
}

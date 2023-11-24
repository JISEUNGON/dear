package com.dearbella.server.dto.response.post;

import com.dearbella.server.domain.Image;
import com.dearbella.server.domain.Tag;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class PostAdminDetailResponseDto {
    private Long postId;
    private String memberName;
    private Long memberId;
    private String memberImage;
    private String createdAt;
    private List<Image> images;
    private Tag tag;
    private String content;

}

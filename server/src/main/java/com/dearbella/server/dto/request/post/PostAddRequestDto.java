package com.dearbella.server.dto.request.post;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostAddRequestDto {
    private Long tag;
    private List<MultipartFile> images;
    private String title;
    private String content;
}

package com.dearbella.server.dto.request.review;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class ReviewAddRequestDto {
    private Long hospitalId;
    private String hospitalName;
    private Long doctorId;
    private String doctorName;
    private List<MultipartFile> befores;
    private List<MultipartFile> afters;
    private String title;
    private String content;
    private Float rate;
}

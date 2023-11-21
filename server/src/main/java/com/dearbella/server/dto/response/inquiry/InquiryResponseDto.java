package com.dearbella.server.dto.response.inquiry;

import com.dearbella.server.domain.Category;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class InquiryResponseDto {
    private Long inquiryId;
    private String category;
    private String content;
    private Boolean answer;
}

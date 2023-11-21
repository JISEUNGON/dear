package com.dearbella.server.dto.response.inquiry;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class InquiryDetailResponseDto {
    private Long inquiryId;
    private String category;
    private String inquiryContent;
    private String answer;
}

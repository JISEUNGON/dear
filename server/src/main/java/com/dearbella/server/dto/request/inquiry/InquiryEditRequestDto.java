package com.dearbella.server.dto.request.inquiry;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class InquiryEditRequestDto {
    private Long inquiryId;
    private String content;
    private Long memberId;
}

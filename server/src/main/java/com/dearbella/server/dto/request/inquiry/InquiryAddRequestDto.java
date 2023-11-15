package com.dearbella.server.dto.request.inquiry;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class InquiryAddRequestDto {
    private Long hospitalId;
    private Long category;
    private String name;
    private Long location;
    private String phoneNumber;
    private String content;
}

package com.dearbella.server.dto.response.inquiry;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class InquiryAdminResponseDto {
    private Long inquiryId;
    private String createAt;
    private String hospitalName;
    private String id;
    private Long totalPages;
}

package com.dearbella.server.dto.response.inquiry;

import com.dearbella.server.domain.Category;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class InquiryDetailDto {
    private Long inquiryId;
    private String category;
    private String name;
    private String phone;
    private String hospitalName;
    private String content;
}

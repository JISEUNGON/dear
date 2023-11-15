package com.dearbella.server.service.inquiry;

import com.dearbella.server.domain.Inquiry;
import com.dearbella.server.dto.request.inquiry.InquiryAddRequestDto;

public interface InquiryService {
    public Inquiry addInquiry(InquiryAddRequestDto dto);
}

package com.dearbella.server.service.inquiry;

import com.dearbella.server.domain.Inquiry;
import com.dearbella.server.dto.request.inquiry.InquiryAddRequestDto;
import com.dearbella.server.dto.response.inquiry.InquiryResponseDto;

import java.util.List;

public interface InquiryService {
    public Inquiry addInquiry(InquiryAddRequestDto dto);

    public List<InquiryResponseDto> findMyInquiries();
}

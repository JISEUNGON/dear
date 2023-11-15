package com.dearbella.server.service.inquiry;

import com.dearbella.server.domain.Inquiry;
import com.dearbella.server.dto.request.inquiry.InquiryAddRequestDto;
import com.dearbella.server.repository.InquiryRepository;
import com.dearbella.server.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class InquiryServiceImpl implements InquiryService {
    private final InquiryRepository inquiryRepository;

    @Override
    public Inquiry addInquiry(InquiryAddRequestDto dto) {
        return inquiryRepository.save(
                Inquiry.builder()
                        .answer(null)
                        .category(dto.getCategory())
                        .content(dto.getContent())
                        .hospitalId(dto.getHospitalId())
                        .title(dto.getContent())
                        .location(dto.getLocation())
                        .member_name(dto.getName())
                        .memberId(JwtUtil.getMemberId())
                        .phoneNumber(dto.getPhoneNumber())
                        .build()
        );
    }
}

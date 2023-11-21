package com.dearbella.server.service.inquiry;

import com.dearbella.server.domain.Inquiry;
import com.dearbella.server.dto.request.inquiry.InquiryAddRequestDto;
import com.dearbella.server.dto.response.inquiry.InquiryResponseDto;
import com.dearbella.server.enums.doctor.CategoryEnum;
import com.dearbella.server.repository.InquiryRepository;
import com.dearbella.server.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

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

    @Override
    @Transactional
    public List<InquiryResponseDto> findMyInquiries() {
        List<InquiryResponseDto> responseDtoList = new ArrayList<>();

        final List<Inquiry> byMemberId = inquiryRepository.findByMemberId(JwtUtil.getMemberId());

        for(Inquiry inquiry: byMemberId) {
            responseDtoList.add(
                    InquiryResponseDto.builder()
                            .inquiryId(inquiry.getInquiryId())
                            .category(CategoryEnum.findByValue(inquiry.getCategory()).name())
                            .content(inquiry.getContent())
                            .answer(inquiry.getAnswer() == null ? false : true)
                            .build()
            );
        }

        return responseDtoList;
    }
}

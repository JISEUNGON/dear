package com.dearbella.server.service.inquiry;

import com.dearbella.server.domain.Hospital;
import com.dearbella.server.domain.Inquiry;
import com.dearbella.server.domain.Member;
import com.dearbella.server.dto.request.inquiry.InquiryAddRequestDto;
import com.dearbella.server.dto.request.inquiry.InquiryEditRequestDto;
import com.dearbella.server.dto.response.inquiry.InquiryAdminResponseDto;
import com.dearbella.server.dto.response.inquiry.InquiryDetailDto;
import com.dearbella.server.dto.response.inquiry.InquiryDetailResponseDto;
import com.dearbella.server.dto.response.inquiry.InquiryResponseDto;
import com.dearbella.server.enums.doctor.CategoryEnum;
import com.dearbella.server.exception.hospital.HospitalIdNotFoundException;
import com.dearbella.server.exception.inquiry.InquiryIdNotFoundException;
import com.dearbella.server.exception.member.MemberIdNotFoundException;
import com.dearbella.server.repository.HospitalRepository;
import com.dearbella.server.repository.InquiryRepository;
import com.dearbella.server.repository.MemberRepository;
import com.dearbella.server.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
public class InquiryServiceImpl implements InquiryService {
    private final InquiryRepository inquiryRepository;
    private final HospitalRepository hospitalRepository;
    private final MemberRepository memberRepository;

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
                        .memberName(dto.getName())
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

    @Override
    @Transactional
    public InquiryDetailResponseDto findById(final Long id) {
        final Inquiry byId = inquiryRepository.findById(id).orElseThrow(
                () -> new InquiryIdNotFoundException(id)
        );

        return InquiryDetailResponseDto.builder()
                .inquiryId(byId.getInquiryId())
                .inquiryContent(byId.getContent())
                .answer(byId.getAnswer())
                .category(CategoryEnum.findByValue(byId.getCategory()).name())
                .build();
    }

    @Override
    @Transactional
    public List<InquiryAdminResponseDto> getAll(final Long page) {
        final Page<Inquiry> all = inquiryRepository.findAll(PageRequest.of(page.intValue(), 12, Sort.by(Sort.Direction.DESC, "createdAt")));
        List<InquiryAdminResponseDto> responseDtoList = new ArrayList<>();

        for(Inquiry inquiry: all) {
            final Hospital hospital = hospitalRepository.findById(inquiry.getHospitalId()).orElseThrow(
                    () -> new HospitalIdNotFoundException(inquiry.getHospitalId())
            );
            final Member member = memberRepository.findById(inquiry.getMemberId()).orElseThrow(
                    () -> new MemberIdNotFoundException(inquiry.getMemberId().toString())
            );

            responseDtoList.add(
                    InquiryAdminResponseDto.builder()
                            .inquiryId(inquiry.getInquiryId())
                            .createAt(inquiry.getCreatedAt().format(DateTimeFormatter.ofPattern("yyy-MM-dd HH:mm:ss")))
                            .hospitalName(hospital.getHospitalName())
                            .id(member.getLoginEmail())
                            .totalPages(Long.valueOf(all.getTotalPages()))
                            .build()
            );
        }

        return responseDtoList;
    }

    @Override
    @Transactional
    public Inquiry answerInquiry(final InquiryEditRequestDto dto) {
        Inquiry inquiry = inquiryRepository.findById(dto.getInquiryId()).orElseThrow(
                () -> new InquiryIdNotFoundException(dto.getInquiryId())
        );

        inquiry.setAnswer(dto.getContent());

        return inquiryRepository.save(inquiry);
    }

    @Override
    @Transactional
    public InquiryDetailDto getDetail(final Long inquiryId) {
        final Inquiry inquiry = inquiryRepository.findById(inquiryId).orElseThrow(
                () -> new InquiryIdNotFoundException(inquiryId)
        );

        return InquiryDetailDto.builder()
                .inquiryId(inquiryId)
                .hospitalName(hospitalRepository.findById(inquiry.getHospitalId()).orElseThrow(() -> new HospitalIdNotFoundException(inquiry.getHospitalId())).getHospitalName())
                .category(CategoryEnum.findByValue(inquiry.getCategory()).name())
                .name(inquiry.getMemberName())
                .phone(inquiry.getPhoneNumber())
                .content(inquiry.getContent())
                .build();
    }
}

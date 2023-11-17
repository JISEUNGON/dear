package com.dearbella.server.service.hospital;

import com.dearbella.server.domain.*;
import com.dearbella.server.dto.request.hospital.HospitalAddRequestDto;
import com.dearbella.server.dto.response.hospital.HospitalResponseDto;
import com.dearbella.server.exception.banner.BannerInfraNotFoundException;
import com.dearbella.server.exception.doctor.DoctorByHospitalNameNotFoundException;
import com.dearbella.server.repository.*;
import com.dearbella.server.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.*;

@Slf4j
@RequiredArgsConstructor
public class HospitalServiceImpl implements HospitalService {
    private final HospitalRepository hospitalRepository;
    private final ImageRepository imageRepository;
    private final InfraRepository infraRepository;
    private final HospitalMemberRepository hospitalMemberRepository;
    private final HospitalReviewRepository hospitalReviewRepository;
    private final DoctorRepository doctorRepository;

    @PostConstruct
    public void main() {
        log.info("called");
    }

    /**
     * TODO
     * 병원 시설 저장
     * */

    @Override
    public Hospital addHospital(final HospitalAddRequestDto dto, List<String> befores, List<String> afters)  {
        Set<Image> beforeImages = new HashSet<>();
        Set<Image> afterImages = new HashSet<>();
        Set<Infra> infraList = new HashSet<>();

        for(String image: befores) {
            beforeImages.add(
                    imageRepository.save(
                        Image.builder()
                                .imageUrl(image)
                              .memberId(JwtUtil.getMemberId())
                              .build()
                    )
            );
        }

        for(String image: afters) {
            afterImages.add(
                    imageRepository.save(
                            Image.builder()
                                    .imageUrl(image)
                                    .memberId(JwtUtil.getMemberId())
                                    .build()
                    )
            );
        }

        for(Long tag: dto.getInfras()) {
            infraList.add(
                    infraRepository.findById(tag).orElseThrow(
                            () -> new BannerInfraNotFoundException(tag)
                    )
            );
        }

        return hospitalRepository.save(
                Hospital.builder()
                        .adminId(JwtUtil.getMemberId())
                        .after(afterImages)
                        .before(beforeImages)
                        .hospitalName(dto.getName())
                        .description(dto.getDescription())
                        .hospitalLocation(dto.getLocation())
                        .description(dto.getDescription())
                        .hospitalVideoLink(dto.getLink())
                        .sequence(dto.getSequence())
                        .totalRate(0F)
                        .infras(infraList)
                        .anesthesiologist(0L)
                        .plasticSurgeon(0L)
                        .dermatologist(0L)
                        .build()
        );
    }

    @Override
    @Transactional
    public List<HospitalResponseDto> getAll(final Long category, final Long sort) {
        log.error("hospital is null");
        List<Hospital> hospitals;
        List<HospitalResponseDto> responseDtos = new ArrayList<>();

        if(sort == 0) {
            hospitals = hospitalRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt"));
        }
        else
            hospitals = hospitalRepository.findAll(Sort.by(Sort.Direction.DESC, "totalRate"));

        String memberIdString = JwtUtil.isExistAccessToken();
        Long memberId;

        if(memberIdString == null)
            memberId = 0L;
        else
            memberId = Long.parseLong(memberIdString);

        for(Hospital hospital: hospitals) {
            Boolean isEmpty = true;
            if(memberId > 0) {
                isEmpty = hospitalMemberRepository.findByHospitalIdAndMemberId(
                        hospital.getHospitalId(),
                        memberId).isEmpty();
            }

            if(category == 0) {
                responseDtos.add(
                        HospitalResponseDto.builder()
                                .hospitalId(hospital.getHospitalId())
                                .hospitalName(hospital.getHospitalName())
                                .isMine(isEmpty ? false : true)
                                .location(hospital.getHospitalLocation())
                                .rate(hospital.getTotalRate())
                                .reviewNum(Long.valueOf(hospitalReviewRepository.findHospitalReviewByHospitalId(hospital.getHospitalId()).size()))
                                .build()
                );
            }
            else
            {
                final List<Doctor> doctors = doctorRepository.findByHospitalName(hospital.getHospitalName());

                for(Doctor doctor: doctors) {
                    if(doctor.contains(category)) {
                        responseDtos.add(
                                HospitalResponseDto.builder()
                                        .hospitalId(hospital.getHospitalId())
                                        .hospitalName(hospital.getHospitalName())
                                        .isMine(isEmpty ? false : true)
                                        .location(hospital.getHospitalLocation())
                                        .rate(hospital.getTotalRate())
                                        .reviewNum(Long.valueOf(hospitalReviewRepository.findHospitalReviewByHospitalId(hospital.getHospitalId()).size()))
                                        .build()
                        );
                    }
                }
            }
        }

        return responseDtos;
    }
}

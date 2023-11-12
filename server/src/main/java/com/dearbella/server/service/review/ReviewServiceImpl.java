package com.dearbella.server.service.review;

import com.dearbella.server.domain.*;
import com.dearbella.server.dto.request.review.ReviewAddRequestDto;
import com.dearbella.server.dto.response.review.ReviewAddResponseDto;
import com.dearbella.server.exception.doctor.DoctorIdNotFoundException;
import com.dearbella.server.exception.hospital.HospitalIdNotFoundException;
import com.dearbella.server.exception.member.MemberIdNotFoundException;
import com.dearbella.server.repository.*;
import com.dearbella.server.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.dearbella.server.config.MapperConfig.modelMapper;

@Slf4j
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;
    private final ImageRepository imageRepository;
    private final MemberRepository memberRepository;
    private final DoctorRepository doctorRepository;
    private final HospitalRepository hospitalRepository;
    private final HospitalReviewRepository hospitalReviewRepository;
    private final DoctorReviewRepository doctorReviewRepository;

    @Override
    @Transactional
    public Review addReview(final ReviewAddRequestDto dto, final List<String> befores, final List<String> afters) {
        List<Image> beforeImages = new ArrayList<>();
        List<Image> afterImages = new ArrayList<>();
        Member member = memberRepository.findById(JwtUtil.getMemberId()).orElseThrow(
                () -> new MemberIdNotFoundException(JwtUtil.getMemberId().toString())
        );
        /*
        Doctor doctor = null;
        Hospital hospital = null;

        if(dto.getDoctorId() != 0L) {
            doctor = doctorRepository.findById(dto.getDoctorId()).orElseThrow(
                    () -> new DoctorIdNotFoundException(dto.getDoctorId())
            );
        }

        if(dto.getHospitalId() != 0L) {
            hospital = hospitalRepository.findById(dto.getHospitalId()).orElseThrow(
                    () -> new HospitalIdNotFoundException(dto.getHospitalId())
            );
        }
        */

        for(String image: befores) {
            beforeImages.add(
                    imageRepository.save(
                            Image.builder()
                                    .imageUrl(image)
                                    .memberId(member.getMemberId())
                                    .build()
                    )
            );
        }

        for(String image: afters) {
            afterImages.add(
                    imageRepository.save(
                            Image.builder()
                                    .imageUrl(image)
                                    .memberId(member.getMemberId())
                                    .build()
                    )
            );
        }

        final Review save = reviewRepository.save(
                Review.builder()
                        .title(dto.getTitle())
                        .content(dto.getContent())
                        .deleted(false)
                        .memberId(member.getMemberId())
                        .hospitalId(dto.getHospitalId())
                        .hospitalName(dto.getHospitalName())
                        .doctorId(dto.getDoctorId())
                        .doctorName(dto.getDoctorName())
                        .rate(dto.getRate())
                        .build()
        );

        doctorReviewRepository.save(
                DoctorReview.builder()
                        .reviewId(save.getReviewId())
                        .doctorId(save.getDoctorId())
                        .build()
        );

        hospitalReviewRepository.save(
                HospitalReview.builder()
                        .reviewId(save.getReviewId())
                        .hospitalId(save.getHospitalId())
                        .build()
        );

        return save;

        /*
        ReviewAddResponseDto response = modelMapper.map(save, ReviewAddResponseDto.class);

        if(doctor != null) {
            final int num = doctorReviewRepository.findDoctorReviewByDoctorId(doctor.getDoctorId()).size();

            doctor.setTotalRate((doctor.getTotalRate() * num + dto.getRate()) / num + 1);
        }

        if(hospital != null) {
            final int num = hospitalReviewRepository.findHospitalReviewByHospitalId(hospital.getHospitalId()).size();

            hospital.setTotalRate((hospital.getTotalRate() * num + dto.getRate()) / num + 1);
        }

        response.setMemberName(member.getNickname());
        response.setLikeNum(0L);
        response.setTotalRate(hospital.getTotalRate());
        response.setDoctorName(doctor.getDoctorName());
        response.setDoctorImage(doctor.getDoctorImage());
        response.setDoctorIntro(doctor.getDescription());
        response.setParts(doctor.getCategories());
        response.setHospitalLocation(hospital.getHospitalLocation());
        response.setDoctorReviewRate(doctor.getTotalRate());
        response.setHospitalReviewRate(hospital.getTotalRate());
        response.setBefores(befores);
        response.setAfters(afters);

        return response;
         */
    }
}

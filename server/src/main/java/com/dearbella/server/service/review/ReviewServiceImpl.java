package com.dearbella.server.service.review;

import com.dearbella.server.domain.*;
import com.dearbella.server.dto.request.review.ReviewAddRequestDto;
import com.dearbella.server.dto.response.review.ReviewAddResponseDto;
import com.dearbella.server.dto.response.review.ReviewDetailResponseDto;
import com.dearbella.server.dto.response.review.ReviewResponseDto;
import com.dearbella.server.enums.doctor.CategoryEnum;
import com.dearbella.server.exception.doctor.DoctorIdNotFoundException;
import com.dearbella.server.exception.hospital.HospitalIdNotFoundException;
import com.dearbella.server.exception.member.MemberIdNotFoundException;
import com.dearbella.server.exception.review.ReviewIdNotFoundException;
import com.dearbella.server.exception.review.ReviewNotFoundException;
import com.dearbella.server.repository.*;
import com.dearbella.server.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.dearbella.server.config.MapperConfig.modelMapper;

@Slf4j
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;
    private final ImageRepository imageRepository;
    private final MemberRepository memberRepository;
    private final DoctorRepository doctorRepository;
    private final HospitalRepository hospitalRepository;
    private final ReviewLikeRepository reviewLikeRepository;

    /**
     * TODO:
     * slice
     * */

    @Override
    @Transactional
    public Review addReview(final ReviewAddRequestDto dto, final List<String> befores, final List<String> afters) {
        List<Image> beforeImages = new ArrayList<>();
        List<Image> afterImages = new ArrayList<>();
        Member member = memberRepository.findById(JwtUtil.getMemberId()).orElseThrow(
                () -> new MemberIdNotFoundException(JwtUtil.getMemberId().toString())
        );

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
                        .befores(beforeImages)
                        .afters(afterImages)
                        .rate(dto.getRate())
                        .viewNum(0L)
                        .build()
        );

        if(doctor != null) {
            final int num = reviewRepository.findByDoctorId(doctor.getDoctorId()).size();

            doctor.setTotalRate(((doctor.getTotalRate() * num + dto.getRate())) / (num + 1));
        }

        if(hospital != null) {
            final int num = reviewRepository.findByHospitalId(hospital.getHospitalId()).size();

            hospital.setTotalRate(((hospital.getTotalRate() * num + dto.getRate())) / (num + 1));
        }

        return save;

        /*
        ReviewAddResponseDto response = modelMapper.map(save, ReviewAddResponseDto.class);

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

    @Override
    @Transactional
    public Set<ReviewResponseDto> findByCategory(final Long category) {
        //정렬
        Sort sort = Sort.by(Sort.Direction.DESC, "viewNum");
        List<Review> reviews = reviewRepository.findByTitleContainingAndDeletedFalse(category == 0 ? "" : CategoryEnum.findByValue(category).name(), sort);
        Set<ReviewResponseDto> responseDtoSet = new HashSet<>();

        if(reviews.isEmpty())
            throw new ReviewNotFoundException();

        for(Review review: reviews) {
            responseDtoSet.add(
                    modelMapper.map(review, ReviewResponseDto.class)
            );
        }

        return responseDtoSet;
    }

    @Override
    @Transactional
    public Set<ReviewResponseDto> findByQuery(final String query) {
        Sort sort = Sort.by(Sort.Direction.DESC, "viewNum");

        List<Review> reviewListByTitle = reviewRepository.findByTitleContainingAndDeletedFalse(query, sort);
        List<Review> reviewListByContent = reviewRepository.findByContentContainingAndDeletedFalse(query, sort);
        List<Review> reviewListByHospitalName = reviewRepository.findByHospitalNameContainingAndDeletedFalse(query, sort);
        List<Review> reviewListByDoctorName = reviewRepository.findByDoctorNameContainingAndDeletedFalse(query, sort);

        Set<ReviewResponseDto> responseDtoSet = new HashSet<>();

        for(Review review: reviewListByTitle) {
            responseDtoSet.add(
                    modelMapper.map(review, ReviewResponseDto.class)
            );
        }

        for(Review review: reviewListByContent) {
            responseDtoSet.add(
                    modelMapper.map(review, ReviewResponseDto.class)
            );
        }

        for(Review review: reviewListByHospitalName) {
            responseDtoSet.add(
                    modelMapper.map(review, ReviewResponseDto.class)
            );
        }

        for(Review review: reviewListByDoctorName) {
            responseDtoSet.add(
                    modelMapper.map(review, ReviewResponseDto.class)
            );
        }

        return responseDtoSet;
    }

    @Override
    @Transactional
    public ReviewDetailResponseDto findById(final Long id) {
        final Review review = reviewRepository.findById(id).orElseThrow(
                () -> new ReviewIdNotFoundException(id)
        );
        Member member = memberRepository.findById(review.getMemberId()).orElseThrow(
                () -> new MemberIdNotFoundException(review.getMemberId().toString())
        );

        ReviewDetailResponseDto response = modelMapper.map(review, ReviewDetailResponseDto.class);

        response.setNickname(member.getNickname());
        response.setProfileImg(member.getProfileImg());

        if(review.getHospitalId().equals(0L))
            response.setRate(review.getRate());
        else
        {
            final Hospital hospital = hospitalRepository.findById(review.getHospitalId()).orElseThrow(
                    () -> new HospitalIdNotFoundException(review.getHospitalId())
            );

            response.setRate(hospital.getTotalRate());
        }
        /**
         * 변경
         * comment size 변경
         * like 했는지 안 했는지
         * 병원 정보 + 좋아요 여부
         * 닥터 정보 + 좋아요 여부
         * */
        response.setCommentNum(0L);
        response.setLikeNum(Long.valueOf(
                reviewLikeRepository.findByReviewId(response.getReviewId()).size())
        );

        return response;
    }
}

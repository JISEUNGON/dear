package com.dearbella.server.service.doctor;

import com.dearbella.server.domain.*;
import com.dearbella.server.dto.request.doctor.DoctorAddRequestDto;
import com.dearbella.server.dto.request.doctor.DoctorDetailResponseDto;
import com.dearbella.server.dto.response.doctor.DoctorResponseDto;
import com.dearbella.server.dto.response.review.ReviewPreviewResponseDto;
import com.dearbella.server.enums.doctor.CategoryEnum;
import com.dearbella.server.exception.doctor.CategoryNotFoundException;
import com.dearbella.server.exception.doctor.DoctorIdNotFoundException;
import com.dearbella.server.exception.member.MemberIdNotFoundException;
import com.dearbella.server.repository.*;
import com.dearbella.server.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class DoctorServiceImpl implements DoctorService {
    private final DoctorRepository doctorRepository;
    private final CareerRepository careerRepository;
    private final IntroLinkRepository introLinkRepository;
    private final CategoryRepository categoryRepository;
    private final DoctorMemberRepository doctorMemberRepository;
    private final ReviewRepository reviewRepository;
    private final MemberRepository memberRepository;

    /**
     * TODO
     * total rate
     * */

    @Override
    @Transactional
    public Doctor addDoctor(final DoctorAddRequestDto dto, String image) {
        List<Career> careers = new ArrayList<>();
        List<IntroLink> videos = new ArrayList<>();
        List<Category> categories = new ArrayList<>();

        for(int i = 0; i < dto.getCareer().size(); i++) {
            careers.add(
                    careerRepository.save(
                            Career.builder()
                                    .careerName(dto.getCareer().get(i))
                                    .careerDate(dto.getDate().get(i))
                                    .build()
                    )
            );
        }

        for(String video: dto.getVideoLinks()) {
            videos.add(
                    introLinkRepository.save(
                            IntroLink.builder()
                                    .linkUrl(video)
                                    .build()
                    )
            );
        }

        for(Long tag: dto.getTags()) {
            categories.add(
                    categoryRepository.findById(tag).orElseThrow(
                            () -> new CategoryNotFoundException(tag)
                    )
            );
        }

        final Doctor save = doctorRepository.save(
                Doctor.builder()
                        .doctorName(dto.getDoctorName())
                        .doctorImage(image)
                        .hospitalName(dto.getHospitalName())
                        .description(dto.getDescription())
                        .adminId(JwtUtil.getMemberId())
                        .career(careers)
                        .categories(categories)
                        .links(videos)
                        .sequence(dto.getSequence())
                        .totalRate(0.0F)
                        .deleted(false)
                        .build()
        );

        return save;
    }

    @Override
    @Transactional
    public List<DoctorResponseDto> findAll(final Long category, final Long sort) {
        Sort sort1;
        List<DoctorResponseDto> responseDtoList = new ArrayList<>();

        if(sort == 0L) {
            sort1 = Sort.by(Sort.Direction.DESC, "totalRate");
        }
        else
            sort1 = Sort.by(Sort.Direction.DESC, "viewNum");

        final List<Doctor> byCategories;

        if(category > 0) {
            byCategories = doctorRepository.findByCategories(
                    Category.builder()
                            .categoryNum(category)
                            .categoryName(CategoryEnum.findByValue(category).name())
                            .build(), sort1
            );
        }
        else
        {
            byCategories = doctorRepository.findAll(sort1);
        }

        Long memberId = JwtUtil.isExistAccessToken() == null ? 0L : JwtUtil.getMemberId(JwtUtil.isExistAccessToken());

        for(Doctor doctor: byCategories) {
            final boolean empty = doctorMemberRepository.findByDoctorIdAndMemberId(doctor.getDoctorId(), memberId).isEmpty();
            final int size = reviewRepository.findByDoctorId(doctor.getDoctorId()).size();

            responseDtoList.add(
                    DoctorResponseDto.builder()
                            .isMine(empty ? false : true)
                            .doctorId(doctor.getDoctorId())
                            .reviewNum(Long.valueOf(size))
                            .parts(doctor.getCategories())
                            .rate(doctor.getTotalRate())
                            .intro(doctor.getDescription())
                            .doctorName(doctor.getDoctorName())
                            .doctorImage(doctor.getDoctorImage())
                            .hospitalName(doctor.getHospitalName())
                            .build()
            );
        }

        return responseDtoList;
    }

    @Override
    @Transactional
    public DoctorDetailResponseDto findById(final Long doctorId) {
        final Doctor doctor = doctorRepository.findById(doctorId).orElseThrow(
                () -> new DoctorIdNotFoundException(doctorId)
        );

        Long memberId = JwtUtil.isExistAccessToken() == null ? 0L : JwtUtil.getMemberId(JwtUtil.isExistAccessToken());

        final boolean empty = doctorMemberRepository.findByDoctorIdAndMemberId(doctorId, memberId).isEmpty();

        final List<Review> byDoctorId = reviewRepository.findByDoctorId(doctorId);

        List<ReviewPreviewResponseDto> reviews = new ArrayList<>();

        for(Review review: byDoctorId) {
            final Member member = memberRepository.findById(review.getMemberId()).orElseThrow(
                    () -> new MemberIdNotFoundException(review.getMemberId().toString())
            );

            final boolean empty1 = doctorMemberRepository.findByDoctorIdAndMemberId(doctorId, member.getMemberId()).isEmpty();

            reviews.add(
                    ReviewPreviewResponseDto.builder()
                            .reviewId(review.getReviewId())
                            .after(review.getAfters().get(0))
                            .before(review.getBefores().get(0))
                            .memberName(member.getNickname())
                            .memberImage(member.getProfileImg())
                            .commentNum(0L)
                            .isLike(empty1 ? false : true)
                            .likeNum(0L)
                            .updatedAt(review.getUpdatedAt())
                            .hospitalName(review.getHospitalName())
                            .rate(review.getRate())
                            .title(review.getTitle())
                            .build()
            );
        }

        DoctorDetailResponseDto response = DoctorDetailResponseDto.builder()
                .doctorId(doctorId)
                .careers(doctor.getCareer())
                .rate(doctor.getTotalRate())
                .description(doctor.getDescription())
                .doctorName(doctor.getDoctorName())
                .hospitalName(doctor.getHospitalName())
                .parts(doctor.getCategories())
                .videos(doctor.getLinks())
                .doctorImage(doctor.getDoctorImage())
                .isMine(empty ? false : true)
                .reviewNum(Long.valueOf(byDoctorId.size()))
                .reviews(reviews)
                .build();


        return response;
    }
}

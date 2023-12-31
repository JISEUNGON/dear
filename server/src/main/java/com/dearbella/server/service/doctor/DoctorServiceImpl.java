package com.dearbella.server.service.doctor;

import com.dearbella.server.domain.*;
import com.dearbella.server.dto.request.doctor.DoctorAddRequestDto;
import com.dearbella.server.dto.request.doctor.DoctorDetailResponseDto;
import com.dearbella.server.dto.request.doctor.DoctorEditRequestDto;
import com.dearbella.server.dto.response.doctor.DoctorAdminResponseDto;
import com.dearbella.server.dto.response.doctor.DoctorResponseDto;
import com.dearbella.server.dto.response.doctor.MyDoctorResponseDto;
import com.dearbella.server.dto.response.review.ReviewPreviewResponseDto;
import com.dearbella.server.enums.doctor.CategoryEnum;
import com.dearbella.server.exception.doctor.CategoryNotFoundException;
import com.dearbella.server.exception.doctor.DoctorIdNotFoundException;
import com.dearbella.server.exception.member.MemberIdNotFoundException;
import com.dearbella.server.repository.*;
import com.dearbella.server.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

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

        return doctorRepository.save(
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
                        .viewNum(0L)
                        .deleted(false)
                        .build()
        );
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
            byCategories = doctorRepository.findByCategoriesAndDeletedFalse(
                    Category.builder()
                            .categoryNum(category)
                            .categoryName(CategoryEnum.findByValue(category).name())
                            .build(), sort1
            );
        }
        else
        {
            byCategories = doctorRepository.findByAndDeletedFalse(sort1);
        }

        Long memberId = JwtUtil.isExistAccessToken() == null ? 0L : JwtUtil.getMemberId(JwtUtil.isExistAccessToken());

        for(Doctor doctor: byCategories) {
            final boolean empty = doctorMemberRepository.findByDoctorIdAndMemberId(doctor.getDoctorId(), memberId).isEmpty();
            final int size = reviewRepository.findByDoctorIdAndDeletedFalse(doctor.getDoctorId()).size();

            responseDtoList.add(
                    DoctorResponseDto.builder()
                            .isMine(empty ? false : true)
                            .doctorId(doctor.getDoctorId())
                            .reviewNum((long) size)
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
        final Doctor doctor = doctorRepository.findByDoctorIdAndDeletedFalse(doctorId).orElseThrow(
                () -> new DoctorIdNotFoundException(doctorId)
        );

        Long memberId = JwtUtil.isExistAccessToken() == null ? 0L : JwtUtil.getMemberId(JwtUtil.isExistAccessToken());

        final boolean empty = doctorMemberRepository.findByDoctorIdAndMemberId(doctorId, memberId).isEmpty();

        final List<Review> byDoctorId = reviewRepository.findByDoctorIdAndDeletedFalse(doctorId);

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
                .reviewNum((long) byDoctorId.size())
                .reviews(reviews)
                .build();


        return response;
    }

    @Override
    @Transactional
    public Set<DoctorResponseDto> findByQuery(final String query) {
        Set<DoctorResponseDto> doctorResponseDtos = new HashSet<>();

        for(Doctor doctor: doctorRepository.findByHospitalNameContainingAndDeletedFalse(query)) {
            final List<Review> byDoctorId = reviewRepository.findByDoctorIdAndDeletedFalse(doctor.getDoctorId());

            doctorResponseDtos.add(
                    DoctorResponseDto.builder()
                            .doctorId(doctor.getDoctorId())
                            .hospitalName(doctor.getHospitalName())
                            .doctorImage(doctor.getDoctorImage())
                            .isMine(false)
                            .rate(doctor.getTotalRate())
                            .reviewNum(Long.valueOf(byDoctorId.size()))
                            .intro(doctor.getDescription())
                            .parts(doctor.getCategories())
                            .doctorName(doctor.getDoctorName())
                            .build()
            );
        }

        for(Doctor doctor: doctorRepository.findByDescriptionAndDeletedFalse(query)) {
            final List<Review> byDoctorId = reviewRepository.findByDoctorIdAndDeletedFalse(doctor.getDoctorId());

            doctorResponseDtos.add(
                    DoctorResponseDto.builder()
                            .doctorId(doctor.getDoctorId())
                            .hospitalName(doctor.getHospitalName())
                            .doctorImage(doctor.getDoctorImage())
                            .isMine(false)
                            .rate(doctor.getTotalRate())
                            .reviewNum(Long.valueOf(byDoctorId.size()))
                            .intro(doctor.getDescription())
                            .parts(doctor.getCategories())
                            .doctorName(doctor.getDoctorName())
                            .build()
            );
        }

        final Optional<Category> byCategoryName = categoryRepository.findByCategoryName(query);

        if(!byCategoryName.isEmpty()) {
            for(Doctor doctor: doctorRepository.findByCategoriesAndDeletedFalse(byCategoryName.get())) {
                final List<Review> byDoctorId = reviewRepository.findByDoctorIdAndDeletedFalse(doctor.getDoctorId());

                doctorResponseDtos.add(
                        DoctorResponseDto.builder()
                                .doctorId(doctor.getDoctorId())
                                .hospitalName(doctor.getHospitalName())
                                .doctorImage(doctor.getDoctorImage())
                                .isMine(false)
                                .rate(doctor.getTotalRate())
                                .reviewNum((long) byDoctorId.size())
                                .intro(doctor.getDescription())
                                .parts(doctor.getCategories())
                                .doctorName(doctor.getDoctorName())
                                .build()
                );
            }
        }

        return doctorResponseDtos;
    }

    @Override
    @Transactional
    public List<MyDoctorResponseDto> findMyDoctors() {
        List<MyDoctorResponseDto> responseDtoList = new ArrayList<>();
        final List<DoctorMember> byMemberId = doctorMemberRepository.findByMemberId(JwtUtil.getMemberId(), Sort.by(Sort.Direction.DESC, "createdAt"));

        for(DoctorMember doctorMember: byMemberId) {
            final Doctor doctor = doctorRepository.findByDoctorIdAndDeletedFalse(doctorMember.getDoctorId()).orElseThrow(
                    () -> new DoctorIdNotFoundException(doctorMember.getDoctorId())
            );
            final List<Review> reviews = reviewRepository.findByDoctorIdAndDeletedFalse(doctor.getDoctorId());

            responseDtoList.add(
                    MyDoctorResponseDto.builder()
                            .doctorId(doctor.getDoctorId())
                            .DoctorImage(doctor.getDoctorImage())
                            .categories(doctor.getCategories())
                            .reviewNum((long) reviews.size())
                            .rate(doctor.getTotalRate())
                            .hospitalName(doctor.getHospitalName())
                            .intro(doctor.getDescription())
                            .doctorName(doctor.getDoctorName())
                            .build()
            );
        }

        return responseDtoList;
    }

    @Override
    @Transactional
    public DoctorMember addWish(final Long doctorId) {
        final Optional<DoctorMember> byDoctorIdAndMemberId = doctorMemberRepository.findByDoctorIdAndMemberId(doctorId, JwtUtil.getMemberId());

        if(byDoctorIdAndMemberId.isEmpty())
            return doctorMemberRepository.save(
                    DoctorMember.builder()
                            .doctorId(doctorId)
                            .memberId(JwtUtil.getMemberId())
                            .build()
            );
        else
            return byDoctorIdAndMemberId.get();
    }

    @Override
    @Transactional
    public void removeWish(final Long doctorId) {
        doctorMemberRepository.deleteByDoctorId(doctorId);
    }

    @Override
    @Transactional
    public List<DoctorAdminResponseDto> getDoctors(final Long page) {
        final Page<Doctor> all = doctorRepository.findByDeletedFalse(PageRequest.of(page.intValue(), 12, Sort.by(Sort.Direction.ASC, "doctorName")));
        List<DoctorAdminResponseDto> responseDtos = new ArrayList<>();

        for(Doctor doctor: all) {
            responseDtos.add(
                    DoctorAdminResponseDto.builder()
                            .doctorId(doctor.getDoctorId())
                            .doctorName(doctor.getDoctorName())
                            .totalPage(Long.valueOf(all.getTotalPages()))
                            .build()
            );
        }

        return responseDtos;
    }

    @Override
    @Transactional
    public Doctor getDoctor(Long doctorId) {
        return doctorRepository.findByDoctorIdAndDeletedFalse(doctorId).orElseThrow(
                () -> new DoctorIdNotFoundException(doctorId)
        );
    }

    @Override
    @Transactional
    public String deleteDoctor(Long doctorId) {
        Doctor doctor = doctorRepository.findByDoctorIdAndDeletedFalse(doctorId).orElseThrow(
                () -> new DoctorIdNotFoundException(doctorId)
        );

        if(doctor.getDeleted())
            return "already deleted";
        else
            doctor.setDeleted(true);

        doctorRepository.save(doctor);

        return "Success";
    }

    @Override
    @Transactional
    public Doctor editDoctor(final DoctorEditRequestDto dto, String image) {
        Doctor doctor = doctorRepository.findById(dto.getDoctorId()).orElseThrow(
                () -> new DoctorIdNotFoundException(dto.getDoctorId())
        );

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

        doctor.setDoctorImage(image);
        doctor.setDoctorName(dto.getDoctorName());
        doctor.setHospitalName(dto.getHospitalName());
        doctor.setLinks(videos);
        doctor.setCareer(careers);
        doctor.setCategories(categories);
        doctor.setDescription(dto.getDescription());

        return doctorRepository.save(doctor);
    }
}

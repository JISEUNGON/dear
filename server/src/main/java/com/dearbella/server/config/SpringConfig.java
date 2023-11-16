package com.dearbella.server.config;

import com.amazonaws.services.s3.AmazonS3;
import com.dearbella.server.repository.*;
import com.dearbella.server.service.banner.BannerService;
import com.dearbella.server.service.banner.BannerServiceImpl;
import com.dearbella.server.service.doctor.DoctorService;
import com.dearbella.server.service.doctor.DoctorServiceImpl;
import com.dearbella.server.service.hospital.HospitalService;
import com.dearbella.server.service.hospital.HospitalServiceImpl;
import com.dearbella.server.service.inquiry.InquiryService;
import com.dearbella.server.service.inquiry.InquiryServiceImpl;
import com.dearbella.server.service.member.MemberService;
import com.dearbella.server.service.member.MemberServiceImpl;
import com.dearbella.server.service.post.PostService;
import com.dearbella.server.service.post.PostServiceImpl;
import com.dearbella.server.service.review.ReviewService;
import com.dearbella.server.service.review.ReviewServiceImpl;
import com.dearbella.server.service.s3.S3UploadService;
import com.dearbella.server.service.s3.S3UploadServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class SpringConfig {
    @Bean
    public MemberService memberService(TokenRepository tokenRepository, MemberRepository memberRepository) {
        return new MemberServiceImpl(memberRepository, tokenRepository);
    }

    @Bean
    public HospitalService hospitalService(HospitalRepository hospitalRepository, ImageRepository imageRepository, InfraRepository infraRepository) {
        return new HospitalServiceImpl(hospitalRepository, imageRepository, infraRepository);
    }

    @Bean
    public S3UploadService s3UploadService(AmazonS3 amazonS3Client) {
        return new S3UploadServiceImpl(amazonS3Client);
    }

    @Bean
    public DoctorService doctorService(DoctorRepository doctorRepository, CareerRepository careerRepository, IntroLinkRepository introLinkRepository, CategoryRepository categoryRepository) {
        return new DoctorServiceImpl(doctorRepository, careerRepository, introLinkRepository, categoryRepository);
    }

    @Bean
    public BannerService bannerService(BannerRepository bannerRepository, InfraRepository infraRepository, ImageRepository imageRepository) {
        return new BannerServiceImpl(bannerRepository, infraRepository, imageRepository);
    }

    @Bean
    public ReviewService reviewService(ReviewRepository reviewRepository, ImageRepository imageRepository, MemberRepository memberRepository, DoctorRepository doctorRepository,
                                       HospitalRepository hospitalRepository, HospitalReviewRepository hospitalReviewRepository,
                                       DoctorReviewRepository doctorReviewRepository, ReviewLikeRepository reviewLikeRepository) {
        return new ReviewServiceImpl(reviewRepository, imageRepository, memberRepository, doctorRepository, hospitalRepository,
                hospitalReviewRepository, doctorReviewRepository, reviewLikeRepository);
    }

    @Bean
    public PostService postService(PostRepository postRepository, ImageRepository imageRepository,
                                   TagRepository tagRepository) {
        return new PostServiceImpl(postRepository, tagRepository, imageRepository);
    }

    @Bean
    public InquiryService inquiryService(InquiryRepository inquiryRepository) {
        return new InquiryServiceImpl(inquiryRepository);
    }
}

package com.dearbella.server.config;

import com.amazonaws.services.s3.AmazonS3;
import com.dearbella.server.repository.*;
import com.dearbella.server.service.banner.BannerService;
import com.dearbella.server.service.banner.BannerServiceImpl;
import com.dearbella.server.service.comment.CommentService;
import com.dearbella.server.service.comment.CommentServiceImpl;
import com.dearbella.server.service.doctor.DoctorService;
import com.dearbella.server.service.doctor.DoctorServiceImpl;
import com.dearbella.server.service.fcm.FCMService;
import com.dearbella.server.service.fcm.FCMServiceImpl;
import com.dearbella.server.service.gmail.GmailService;
import com.dearbella.server.service.gmail.GmailServiceImpl;
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
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class SpringConfig {
    private final PasswordEncoder passwordEncoder;
    private final JavaMailSender javaMailSender;

    @Bean
    public MemberService memberService(TokenRepository tokenRepository, MemberRepository memberRepository,
                                       AdminRepository adminRepository, MemberDeleteRepository memberDeleteRepository
                                        ,MemberIpRepository memberIpRepository
                                        ,CommentRepository commentRepository
                                        ,ReviewRepository reviewRepository
                                        ,PostRepository postRepository) {
        return new MemberServiceImpl(memberRepository, tokenRepository, adminRepository, memberDeleteRepository, passwordEncoder, memberIpRepository
                                    , commentRepository, reviewRepository, postRepository);
    }

    @Bean
    public S3UploadService s3UploadService(AmazonS3 amazonS3Client) {
        return new S3UploadServiceImpl(amazonS3Client);
    }

    @Bean
    public DoctorService doctorService(DoctorRepository doctorRepository, CareerRepository careerRepository, IntroLinkRepository introLinkRepository,
                                       CategoryRepository categoryRepository,
                                       DoctorMemberRepository doctorMemberRepository,
                                       ReviewRepository reviewRepository,
                                       MemberRepository memberRepository) {
        return new DoctorServiceImpl(doctorRepository, careerRepository, introLinkRepository, categoryRepository, doctorMemberRepository, reviewRepository
        ,memberRepository);
    }

    @Bean
    public BannerService bannerService(BannerRepository bannerRepository, InfraRepository infraRepository, ImageRepository imageRepository,
                                       ReviewRepository reviewRepository, HospitalRepository hospitalRepository) {
        return new BannerServiceImpl(bannerRepository, infraRepository, imageRepository, reviewRepository, hospitalRepository);
    }

    @Bean
    public ReviewService reviewService(ReviewRepository reviewRepository, ImageRepository imageRepository, MemberRepository memberRepository, DoctorRepository doctorRepository,
                                       HospitalRepository hospitalRepository,
                                       ReviewLikeRepository reviewLikeRepository) {
        return new ReviewServiceImpl(reviewRepository, imageRepository, memberRepository, doctorRepository, hospitalRepository
                , reviewLikeRepository);
    }

    @Bean
    public PostService postService(PostRepository postRepository, ImageRepository imageRepository,
                                   TagRepository tagRepository, MemberRepository memberRepository, PostLikeRepository postLikeRepository) {
        return new PostServiceImpl(postRepository, tagRepository, imageRepository, memberRepository, postLikeRepository);
    }

    @Bean
    public InquiryService inquiryService(InquiryRepository inquiryRepository, HospitalRepository hospitalRepository,
                                         MemberRepository memberRepository) {
        return new InquiryServiceImpl(inquiryRepository, hospitalRepository, memberRepository);
    }

    @Bean
    public HospitalService hospitalService(
            final DoctorRepository doctorRepository,
            final HospitalMemberRepository hospitalMemberRepository,
            final InfraRepository infraRepository,
            final ImageRepository imageRepository,
            final HospitalRepository hospitalRepository,
            final ReviewRepository reviewRepository,
            final DoctorMemberRepository doctorMemberRepository
    ) {
        return new HospitalServiceImpl(
                hospitalRepository,
                imageRepository,
                infraRepository ,
                hospitalMemberRepository,
                doctorRepository,
                reviewRepository,
                doctorMemberRepository
        );
    }

    @Bean
    public CommentService commentService(CommentRepository commentRepository,  MemberRepository memberRepository,
                                         CommentLikeRepository commentLikeRepository,
                                         DoctorResponseRepository doctorResponseRepository) {
        return new CommentServiceImpl(commentRepository, memberRepository, commentLikeRepository, doctorResponseRepository);
    }

    @Bean
    public FCMService fcmService() {
        return new FCMServiceImpl();
    }

    @Bean
    public GmailService gmailService() {
        return new GmailServiceImpl(javaMailSender);
    }
}

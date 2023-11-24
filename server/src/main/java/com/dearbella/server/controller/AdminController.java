package com.dearbella.server.controller;

import com.dearbella.server.domain.*;
import com.dearbella.server.dto.request.admin.AdminCreateRequestDto;
import com.dearbella.server.dto.request.admin.AdminEditRequestDto;
import com.dearbella.server.dto.request.banner.BannerAddRequestDto;
import com.dearbella.server.dto.request.banner.BannerEditRequestDto;
import com.dearbella.server.dto.request.comment.CommentDoctorRequestDto;
import com.dearbella.server.dto.request.doctor.DoctorAddRequestDto;
import com.dearbella.server.dto.request.hospital.HospitalAddRequestDto;
import com.dearbella.server.dto.request.inquiry.InquiryEditRequestDto;
import com.dearbella.server.dto.response.admin.AdminResponseDto;
import com.dearbella.server.dto.response.banner.BannerAdminResponseDto;
import com.dearbella.server.dto.response.banner.BannerResponseDto;
import com.dearbella.server.dto.response.doctor.DoctorAdminResponseDto;
import com.dearbella.server.dto.response.inquiry.InquiryAdminResponseDto;
import com.dearbella.server.dto.response.inquiry.InquiryDetailDto;
import com.dearbella.server.dto.response.inquiry.InquiryDetailResponseDto;
import com.dearbella.server.dto.response.inquiry.InquiryResponseDto;
import com.dearbella.server.dto.response.post.PostAdminDetailResponseDto;
import com.dearbella.server.dto.response.post.PostAdminResponseDto;
import com.dearbella.server.dto.response.post.PostDetailResponseDto;
import com.dearbella.server.dto.response.review.ReviewAdminResponseDto;
import com.dearbella.server.repository.BannerRepository;
import com.dearbella.server.service.banner.BannerService;
import com.dearbella.server.service.comment.CommentService;
import com.dearbella.server.service.doctor.DoctorService;
import com.dearbella.server.service.hospital.HospitalService;
import com.dearbella.server.service.inquiry.InquiryService;
import com.dearbella.server.service.member.MemberService;
import com.dearbella.server.service.post.PostService;
import com.dearbella.server.service.review.ReviewService;
import com.dearbella.server.service.s3.S3UploadService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
@Slf4j
@Api(tags = {"관리자 API"})
public class AdminController {
    private final HospitalService hospitalService;
    private final S3UploadService s3UploadService;
    private final DoctorService doctorService;
    private final BannerService bannerService;
    private final CommentService commentService;
    private final MemberService memberService;
    private final InquiryService inquiryService;
    private final PostService postService;
    private final ReviewService reviewService;

    /**
     * hospital API
     * */
    @ApiOperation("병원 정보 넣기")
    @PostMapping(value = "/hospital/save", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @Transactional
    public ResponseEntity<Hospital> saveHospital(@ModelAttribute HospitalAddRequestDto dto) throws IOException {
        List<String> befores = new ArrayList<>();
        List<String> afters = new ArrayList<>();
        List<String> banners = new ArrayList<>();

        for(MultipartFile before: dto.getBefores()) {
            befores.add(
                    s3UploadService.upload(before, "dearbella/hospital/before", false)
            );
        }

        for(MultipartFile after: dto.getAfters()) {
            afters.add(
                    s3UploadService.upload(after, "dearbella/hospital/after", false)
            );
        }

        for(MultipartFile banner: dto.getBanners()) {
            banners.add(
                    s3UploadService.upload(banner, "dearbella/hospital/banner", false)
            );
        }

        return ResponseEntity.ok(hospitalService.addHospital(dto, befores, afters, banners));
    }

    /**
     * Doctor API
     * */
    @ApiOperation("의사 정보 넣기")
    @PostMapping(value = "/doctor/save", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @Transactional
    public ResponseEntity<Doctor> saveDoctor(@ModelAttribute DoctorAddRequestDto dto) throws IOException {
        final String upload = s3UploadService.upload(dto.getImage(), "dearbella/doctor", false);

        return ResponseEntity.ok(doctorService.addDoctor(dto, upload));
    }

    @ApiOperation("의사 정보 전체 조회")
    @GetMapping("/doctor/all")
    public ResponseEntity<List<DoctorAdminResponseDto>> getDoctors(@RequestParam Long page) {
        return ResponseEntity.ok(doctorService.getDoctors(page));
    }

    @ApiOperation("의사 정보 조회")
    @GetMapping("/doctor/detail")
    public ResponseEntity<Doctor> getDoctorInfo(@RequestParam Long doctorId) {
        return ResponseEntity.ok(doctorService.getDoctor(doctorId));
    }

    @ApiOperation("의사 삭제")
    @DeleteMapping("/doctor/delete")
    public ResponseEntity<String> deleteDoctor(@RequestParam Long doctorId) {
        return ResponseEntity.ok(doctorService.deleteDoctor(doctorId));
    }

    /**
     * Banner API
     * */
    @ApiOperation("배너 만들기")
    @PostMapping(value = "/banner/save", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @Transactional
    public ResponseEntity<Banner> saveBanner(@ModelAttribute BannerAddRequestDto dto) throws IOException {
        List<String> mainImages = new ArrayList<>();
        List<String> detailImages = new ArrayList<>();

        for(MultipartFile file: dto.getBannerImages()) {
            mainImages.add(s3UploadService.upload(file, "dearbella/banner/main", false));
        }

        for(MultipartFile file: dto.getDetailImages()) {
            detailImages.add(s3UploadService.upload(file, "dearbella/banner/detail", false));
        }

        return ResponseEntity.ok(bannerService.addBanner(dto, mainImages, detailImages));
    }

    @ApiOperation("배너 수정")
    @PostMapping(value = "/banner/edit", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @Transactional
    public ResponseEntity<Banner> editBanner(@ModelAttribute BannerEditRequestDto dto) throws IOException {
        List<String> mainImages = new ArrayList<>();
        List<String> detailImages = new ArrayList<>();

        for(MultipartFile file: dto.getBannerImages()) {
            mainImages.add(s3UploadService.upload(file, "dearbella/banner/main", false));
        }

        for(MultipartFile file: dto.getDetailImages()) {
            detailImages.add(s3UploadService.upload(file, "dearbella/banner/detail", false));
        }

        return ResponseEntity.ok(bannerService.editBanner(dto, mainImages, detailImages));
    }

    @ApiOperation("배너 조회")
    @GetMapping("/banner/all")
    public ResponseEntity<List<BannerAdminResponseDto>> getBanners(@RequestParam Long location, @RequestParam Long page) {
        return ResponseEntity.ok(bannerService.getBanners(location, page));
    }

    @ApiOperation("배너 상세 조회")
    @GetMapping("/banner/detail")
    public ResponseEntity<Banner> getBannerDetail(@RequestParam Long bannerId) {
        return ResponseEntity.ok(bannerService.getBanner(bannerId));
    }

    /**
     * Comment API
     * */
    @ApiOperation("원장이 댓글 남기기")
    @PostMapping("/comment/add")
    public ResponseEntity<DoctorResponse> addComment(@RequestBody CommentDoctorRequestDto dto) {
        return ResponseEntity.ok(commentService.addDoctorResponse(dto));
    }

    /**
     * Admin Id API
     * */
    @ApiOperation("관리자 계정 생성")
    @PostMapping("/user/add")
    public ResponseEntity<Admin> createAdmin(@RequestBody AdminCreateRequestDto dto) {
        return ResponseEntity.ok(memberService.createAdmin(dto));
    }

    @ApiOperation("관리자 계정 삭제")
    @DeleteMapping("/user/delete")
    public ResponseEntity<String> deleteAdmin(@RequestParam Long memberId) {
        return ResponseEntity.ok(memberService.deleteAdmin(memberId));
    }

    @ApiOperation("관리자 계정 조회")
    @GetMapping("/user/all")
    public ResponseEntity<List<AdminResponseDto>> getAllAdmin(@RequestParam Long page) {
        return ResponseEntity.ok(memberService.getAllAdmin(page));
    }

    @ApiOperation("관리자 정보 수정")
    @PostMapping("/user/edit")
    public ResponseEntity<AdminResponseDto> editAdmin(@RequestBody AdminEditRequestDto dto) {
        return ResponseEntity.ok(memberService.editAdmin(dto));
    }

    /**
     * Inquiry API
     * */
    @ApiOperation("문의 글 불러오기")
    @GetMapping("/inquiry/all")
    public ResponseEntity<List<InquiryAdminResponseDto>> getAllInquiries(@RequestParam Long page) {
        return ResponseEntity.ok(inquiryService.getAll(page));
    }

    @ApiOperation("문의 상세 보기")
    @GetMapping("/inquiry/detail")
    public ResponseEntity<InquiryDetailDto> getInquiry(@RequestParam Long inquiryId) {
        return ResponseEntity.ok(inquiryService.getDetail(inquiryId));
    }

    @ApiOperation("문의 답변하기")
    @PostMapping("/inquiry/answer")
    public ResponseEntity<Inquiry> answerInquiry(@RequestBody InquiryEditRequestDto dto) {
        return ResponseEntity.ok(inquiryService.answerInquiry(dto));
    }

    /**
     * community API
     * */
    @ApiOperation("커뮤니티 글 전체 조회")
    @GetMapping("/community/all")
    public ResponseEntity<List<PostAdminResponseDto>> getPosts(@RequestParam Long category, @RequestParam Long page) {
        return ResponseEntity.ok(postService.findAllByCategory(category, page));
    }

    @ApiOperation("커뮤니티 글 삭제")
    @DeleteMapping("/community/delete")
    public ResponseEntity<String> deletePost(@RequestParam Long postId) {
        return ResponseEntity.ok(postService.deletePost(postId));
    }

    @ApiOperation("커뮤니티 글 상세보기")
    @GetMapping("/community/detail")
    public ResponseEntity<PostAdminDetailResponseDto> getPostDetail(@RequestParam Long postId) {
        return ResponseEntity.ok(postService.getDetail(postId));
    }

    /**
     * review API
     * */
    @ApiOperation("리뷰전체 조회")
    @GetMapping("/review/all")
    public ResponseEntity<List<ReviewAdminResponseDto>> getReviews(Long page) {
        return ResponseEntity.ok(reviewService.getReviews(page));
    }
}
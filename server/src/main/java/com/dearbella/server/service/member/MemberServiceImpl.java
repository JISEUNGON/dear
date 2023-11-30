package com.dearbella.server.service.member;

import com.dearbella.server.domain.*;
import com.dearbella.server.dto.request.admin.AdminCreateRequestDto;
import com.dearbella.server.dto.request.admin.AdminEditRequestDto;
import com.dearbella.server.dto.request.login.AdminLoginRequestDto;
import com.dearbella.server.dto.response.admin.AdminResponseDto;
import com.dearbella.server.dto.response.login.LoginResponseDto;
import com.dearbella.server.dto.response.member.MemberAdminResponseDto;
import com.dearbella.server.dto.response.member.MemberBanResponseDto;
import com.dearbella.server.exception.hospital.HospitalIdNotFoundException;
import com.dearbella.server.exception.login.AdminLoginException;
import com.dearbella.server.exception.member.MemberIdNotFoundException;
import com.dearbella.server.exception.member.MemberLoginEmailNotFoundException;
import com.dearbella.server.repository.*;
import com.dearbella.server.util.JwtUtil;
import com.dearbella.server.vo.GoogleIdTokenVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.dearbella.server.config.MapperConfig.modelMapper;

@RequiredArgsConstructor
@Slf4j
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;
    private final TokenRepository tokenRepository;
    private final AdminRepository adminRepository;
    private final MemberDeleteRepository memberDeleteRepository;
    private final PasswordEncoder passwordEncoder;
    private final MemberIpRepository memberIpRepository;
    private final CommentRepository commentRepository;
    private final ReviewRepository reviewRepository;
    private final PostRepository postRepository;

    @Override
    @Transactional
    public LoginResponseDto signUp(final GoogleIdTokenVo idTokenVo) {
        Authority authority = Authority.builder()
                .authorityName("ROLE_USER")
                .build();

        Member save = memberRepository.save(
                Member.builder()
                        .memberId(System.currentTimeMillis())
                        .loginEmail(idTokenVo.getEmail())
                        .profileImg(idTokenVo.getPicture())
                        .phone(null)
                        .nickname(idTokenVo.getName())
                        .authorities(List.of(authority))
                        .ban(false)
                        .build()
        );

        log.info("member: {}", save);

        final Token token = tokenRepository.save(
                Token.builder()
                        .memberId(save.getMemberId())
                        .accessToken(JwtUtil.createJwt(save.getMemberId()))
                        .accessTokenExpiredAt(LocalDate.now().plusDays(10))
                        .refreshToken(JwtUtil.createRefreshToken(save.getMemberId()))
                        .refreshTokenExpiredAt(LocalDate.now().plusYears(1))
                        .build()
        );

        return LoginResponseDto.of(save, token);
    }

    @Override
    @Transactional
    public LoginResponseDto signIn(final GoogleIdTokenVo idTokenVo) {
        LoginResponseDto responseDto = modelMapper.map(memberRepository.findMemberByLoginEmail(idTokenVo.getEmail()).orElseThrow(
                () -> new MemberLoginEmailNotFoundException(idTokenVo.getEmail())
        ), LoginResponseDto.class);

        final Token token = tokenRepository.findById(responseDto.getMemberId()).orElseThrow(
                () -> new MemberIdNotFoundException(responseDto.getMemberId().toString())
        );

        responseDto.setAccessToken(token.getAccessToken());
        responseDto.setRefreshToken(token.getRefreshToken());

        return responseDto;
    }

    @Override
    public Boolean isMember(final String email) {
        final Optional<Member> response = memberRepository.findMemberByLoginEmail(email);

        return response.isEmpty();
    }

    @Override
    public Member findById() {
        return memberRepository.findById(JwtUtil.getMemberId()).orElseThrow(
                () -> new MemberIdNotFoundException(JwtUtil.getMemberId().toString())
        );
    }

    @Override
    @Transactional
    public Admin createAdmin(AdminCreateRequestDto dto) {
        Long memberId = System.currentTimeMillis();

        Member roleAdmin = memberRepository.save(
                Member.builder()
                        .memberId(memberId)
                        .authorities(List.of(
                                Authority.builder()
                                        .authorityName("ROLE_ADMIN")
                                        .build()
                        ))
                        .ban(false)
                        .phone(null)
                        .profileImg("https://dearbella-bucket.s3.ap-northeast-2.amazonaws.com/profile.png")
                        .loginEmail(dto.getUserId())
                        .nickname(dto.getHospitalName())
                        .build()
        );

        tokenRepository.save(
                Token.builder()
                        .memberId(memberId)
                        .accessToken(JwtUtil.createJwt(memberId))
                        .refreshToken(JwtUtil.createRefreshToken(memberId))
                        .accessTokenExpiredAt(LocalDate.now().plusYears(1L))
                        .refreshTokenExpiredAt(LocalDate.now().plusYears(1L))
                        .build()
        );

        return adminRepository.save(
                Admin.builder()
                        .memberId(memberId)
                        .adminId(dto.getUserId())
                        .adminPassword(passwordEncoder.encode(dto.getPassword()))
                        .hospitalId(dto.getHospitalId())
                        .hospitalName(dto.getHospitalName())
                        .build()
        );
    }

    @Override
    @Transactional
    public String signOut() {
        Member member = memberRepository.findById(JwtUtil.getMemberId()).orElseThrow(
                () -> new MemberIdNotFoundException(JwtUtil.getMemberId().toString())
        );


        memberRepository.delete(member);

        tokenRepository.deleteById(member.getMemberId());

        memberDeleteRepository.save(modelMapper.map(member, MemberDelete.class));

        final List<Comment> comments = commentRepository.findByMemberIdAndDeletedFalse(member.getMemberId());

        for (Comment comment: comments) {
            comment.setDeleted(true);
            commentRepository.save(comment);
        }

        final List<Review> reviews = reviewRepository.findByMemberIdAndDeletedFalse(member.getMemberId());

        for(Review review: reviews) {
            review.setDeleted(true);
            reviewRepository.save(review);
        }

        final List<Post> posts = postRepository.findByMemberId(JwtUtil.getMemberId(), Sort.by(Sort.Direction.DESC, "findByMemberIdAndDeletedFalse"));

        for(Post post: posts) {
            post.setDeleted(true);

            postRepository.save(post);
        }

        return "success";
    }

    @Override
    @Transactional
    public String deleteAdmin(final Long memberId) {
        adminRepository.deleteById(memberId);

        Member member = memberRepository.findById(memberId).orElseThrow(
                () -> new MemberIdNotFoundException(memberId.toString())
        );

        memberRepository.delete(member);

        tokenRepository.deleteById(memberId);

        memberDeleteRepository.save(modelMapper.map(member, MemberDelete.class));

        return "success";
    }

    @Override
    @Transactional
    public List<AdminResponseDto> getAllAdmin(final Long page) {
        final Page<Admin> all = adminRepository.findAll(PageRequest.of(page.intValue(), 11, Sort.by(Sort.Direction.DESC, "createdAt")));
        List<AdminResponseDto> responseDtoList = new ArrayList<>();

        for(Admin admin: all) {
            AdminResponseDto map = modelMapper.map(admin, AdminResponseDto.class);
            map.setTotalPages(Long.valueOf(all.getTotalPages()));

            responseDtoList.add(
                    map
            );
        }

        return responseDtoList;
    }

    @Override
    @Transactional
    public AdminResponseDto editAdmin(final AdminEditRequestDto dto) {
        Member member = memberRepository.findById(dto.getMemberId()).orElseThrow(
                () -> new MemberIdNotFoundException(dto.getMemberId().toString())
        );
        Admin admin = adminRepository.findById(dto.getMemberId()).orElseThrow(
                () -> new MemberIdNotFoundException(dto.getMemberId().toString())
        );

        if(dto.getUserId() != null) {
            admin.setAdminId(dto.getUserId());
            member.setLoginEmail(dto.getUserId());

            memberRepository.save(member);
        }

        if(dto.getPassword() != null)
            admin.setAdminPassword(passwordEncoder.encode(dto.getPassword()));

        return modelMapper.map(adminRepository.save(admin), AdminResponseDto.class);
    }

    @Override
    public Token login(final AdminLoginRequestDto dto) {
        final Admin admin = adminRepository.findByAdminId(dto.getUserId()).orElseThrow(
                () -> new AdminLoginException("id")
        );

        if(passwordEncoder.matches(dto.getPassword(), admin.getAdminPassword()))
            return tokenRepository.findById(admin.getMemberId()).orElseThrow(
                    () -> new MemberIdNotFoundException(admin.getMemberId().toString())
            );
        else
            throw new AdminLoginException("password");
    }

    @Override
    @Transactional
    public List<MemberAdminResponseDto> findAll(final Long page) {
        final Page<MemberIp> all = memberIpRepository.findAll(PageRequest.of(page.intValue(), 12, Sort.by(Sort.Direction.DESC, "accessAt")));
        List<MemberAdminResponseDto> responseDtoList = new ArrayList<>();

        for(MemberIp ip: all) {
            final Member member = memberRepository.findById(ip.getMemberId()).orElseThrow(
                    () -> new MemberIdNotFoundException(ip.getMemberId().toString())
            );

            responseDtoList.add(
                    MemberAdminResponseDto.builder()
                            .memberId(ip.getMemberId())
                            .userIp(ip.getIp())
                            .totalPage(Long.valueOf(all.getTotalPages()))
                            .userId(member.getLoginEmail())
                            .build()
            );
        }

        return responseDtoList;
    }

    @Override
    @Transactional
    public List<MemberBanResponseDto> findAllByBan(final Long page) {
        Page<Member> members = memberRepository.findAll(PageRequest.of(page.intValue(), 10, Sort.by(Sort.Direction.ASC, "loginEmail")));
        List<MemberBanResponseDto> responseDtoList = new ArrayList<>();

        for(Member member: members) {
            responseDtoList.add(
                    MemberBanResponseDto.builder()
                            .memberId(member.getMemberId())
                            .userId(member.getLoginEmail())
                            .isBan(member.getBan())
                            .totalPage(Long.valueOf(members.getTotalPages()))
                            .build()
            );
        }

        return responseDtoList;
    }

    @Override
    @Transactional
    public String banMember(final Long memberId) {
        final Member member = memberRepository.findById(memberId).orElseThrow(
                () -> new MemberIdNotFoundException(memberId.toString())
        );

        if(member.getBan()) {
            member.setBan(false);
            return "ban";
        }
        else {
            member.setBan(true);

            return "release";
        }
    }

    @Override
    public String getMemberName() {
        return memberRepository.findById(JwtUtil.getMemberId()).orElseThrow(
                () -> new MemberIdNotFoundException(JwtUtil.getMemberId().toString())
        ).getNickname();
    }

    @Override
    public String getMemberName(final Long memberId) {
        return memberRepository.findById(memberId).orElseThrow(
                () -> new MemberIdNotFoundException(memberId.toString())
        ).getNickname();
    }
}

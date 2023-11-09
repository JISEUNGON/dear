package com.dearbella.server.service.member;

import com.dearbella.server.domain.Authority;
import com.dearbella.server.domain.Member;
import com.dearbella.server.domain.Token;
import com.dearbella.server.dto.response.login.LoginResponseDto;
import com.dearbella.server.exception.member.MemberIdNotFoundException;
import com.dearbella.server.exception.member.MemberLoginEmailNotFoundException;
import com.dearbella.server.repository.MemberRepository;
import com.dearbella.server.repository.TokenRepository;
import com.dearbella.server.util.JwtUtil;
import com.dearbella.server.vo.GoogleIdTokenVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static com.dearbella.server.config.MapperConfig.modelMapper;

@RequiredArgsConstructor
@Slf4j
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;
    private final TokenRepository tokenRepository;

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
                        .signOut(false)
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
}

package com.dearbella.server.service.token;

import com.dearbella.server.domain.Member;
import com.dearbella.server.domain.Token;
import com.dearbella.server.dto.response.token.TokenResponseDto;
import com.dearbella.server.exception.member.MemberIdNotFoundException;
import com.dearbella.server.exception.token.TokenNotFoundException;
import com.dearbella.server.repository.MemberRepository;
import com.dearbella.server.repository.TokenRepository;
import com.dearbella.server.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static com.dearbella.server.config.MapperConfig.modelMapper;

@Slf4j
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {
    private final TokenRepository tokenRepository;
    private final MemberRepository memberRepository;

    @Override
    public Boolean isExpired() {
        return JwtUtil.isExpired(JwtUtil.getAccessToken());
    }

    @Override
    @Transactional
    public TokenResponseDto reissueAccessToken(Long memberId) {
        String accessToken = JwtUtil.getAccessToken();
        String refreshToken = JwtUtil.getRefreshToken();

        Token token = tokenRepository.findByAccessTokenAndRefreshToken(accessToken,refreshToken)
                .orElseThrow(() -> new TokenNotFoundException());

        Member member = memberRepository.findById(memberId)
                        .orElseThrow(() -> new MemberIdNotFoundException(memberId.toString()));

        token.setAccessToken(JwtUtil.createJwt(member.getMemberId()));
        token.setAccessTokenExpiredAt(LocalDate.now().plusDays(JwtUtil.ACCESS_TOKEN_EXPIRE_TIME));
        tokenRepository.save(token);

        return modelMapper.map(token, TokenResponseDto.class);
    }
}

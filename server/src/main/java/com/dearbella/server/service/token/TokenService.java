package com.dearbella.server.service.token;

import com.dearbella.server.dto.response.token.TokenResponseDto;

public interface TokenService {
    Boolean  isExpired();
    TokenResponseDto reissueAccessToken(Long memberId);
}

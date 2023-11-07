package com.dearbella.server.dto.response.token;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class TokenResponseDto {
    private String accessToken;
    private String refreshToken;
    private Long memberId;
    private LocalDate accessTokenExpiredAt;
    private LocalDate refreshTokenExpiredAt;
}
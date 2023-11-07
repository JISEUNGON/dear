package com.dearbella.server.dto.response.login;

import com.dearbella.server.domain.Member;
import com.dearbella.server.domain.Token;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;

@Getter
@Setter
@Builder
@Slf4j
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponseDto {
    private Long memberId;
    private String accessToken;
    private String refreshToken;
    private String nickname;
    private String email;
    private String img;
    private Boolean pro;
    private Boolean ban;

    public static LoginResponseDto of(Member user, Token token, ModelMapper mapper) {
        LoginResponseDto map = mapper.map(user, LoginResponseDto.class);

        map.setAccessToken(token.getAccessToken());
        map.setRefreshToken(token.getRefreshToken());

        return map;
    }
}

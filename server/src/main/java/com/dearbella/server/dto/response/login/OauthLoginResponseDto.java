package com.dearbella.server.dto.response.login;

import com.dearbella.server.domain.Member;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OauthLoginResponseDto {
    private String nickname;
    private String email;
    private String profileImg;
    private Long loginType;

    public static OauthLoginResponseDto of(Member user) {
        return OauthLoginResponseDto.builder()
                .nickname(user.getNickname())
                .email(user.getLoginEmail())
                .profileImg(user.getProfileImg())
                .build();
    }
}

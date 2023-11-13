package com.dearbella.server.service.member;

import com.dearbella.server.domain.Member;
import com.dearbella.server.dto.response.login.LoginResponseDto;
import com.dearbella.server.vo.GoogleIdTokenVo;

public interface MemberService {
    public LoginResponseDto signUp(GoogleIdTokenVo idTokenVo);
    public LoginResponseDto signIn(GoogleIdTokenVo idTokenVo);
    public Boolean isMember(String email);

    public Member findById();
}

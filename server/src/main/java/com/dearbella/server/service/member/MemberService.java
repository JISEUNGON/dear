package com.dearbella.server.service.member;

import com.dearbella.server.domain.Admin;
import com.dearbella.server.domain.Member;
import com.dearbella.server.dto.request.admin.AdminCreateRequestDto;
import com.dearbella.server.dto.response.admin.AdminResponseDto;
import com.dearbella.server.dto.response.login.LoginResponseDto;
import com.dearbella.server.vo.GoogleIdTokenVo;
import org.springframework.data.domain.Page;

import java.util.List;

public interface MemberService {
    public LoginResponseDto signUp(GoogleIdTokenVo idTokenVo);
    public LoginResponseDto signIn(GoogleIdTokenVo idTokenVo);
    public Boolean isMember(String email);
    public Member findById();
    public Admin createAdmin(AdminCreateRequestDto dto);
    public String signOut();
    public String deleteAdmin(Long memberId);
    public List<AdminResponseDto> getAllAdmin(Long page);
}

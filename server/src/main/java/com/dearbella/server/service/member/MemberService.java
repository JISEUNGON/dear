package com.dearbella.server.service.member;

import com.dearbella.server.domain.Admin;
import com.dearbella.server.domain.Member;
import com.dearbella.server.domain.Token;
import com.dearbella.server.dto.request.admin.AdminCreateRequestDto;
import com.dearbella.server.dto.request.admin.AdminEditRequestDto;
import com.dearbella.server.dto.request.login.AdminLoginRequestDto;
import com.dearbella.server.dto.response.admin.AdminResponseDto;
import com.dearbella.server.dto.response.login.LoginResponseDto;
import com.dearbella.server.dto.response.member.MemberAdminResponseDto;
import com.dearbella.server.dto.response.member.MemberBanResponseDto;
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
    public AdminResponseDto editAdmin(AdminEditRequestDto dto);
    public Token login(AdminLoginRequestDto dto);
    public List<MemberAdminResponseDto> findAll(Long page);
    public List<MemberBanResponseDto> findAllByBan(Long page);
    public String banMember(Long memberId);
    public String getMemberName();
}

package com.dearbella.server.controller;

import com.dearbella.server.service.member.MemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
@Slf4j
@Api(tags = {"멤버 API"})
public class MemberController {
    private final MemberService memberService;

    @ApiOperation("회원탈퇴")
    @DeleteMapping("/sign-out")
    public ResponseEntity<String> signOut() {
        return ResponseEntity.ok(memberService.signOut());
    }
}

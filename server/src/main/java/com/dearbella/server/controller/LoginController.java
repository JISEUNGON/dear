package com.dearbella.server.controller;

import com.dearbella.server.dto.response.login.LoginResponseDto;
import com.dearbella.server.service.member.MemberService;
import com.dearbella.server.service.oauth.OauthService;
import com.dearbella.server.vo.GoogleIdTokenVo;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping(value = "/login")
@Api(tags = {"로그인 API"})
@RequiredArgsConstructor
public class LoginController {
    private final OauthService oauthService;
    private final MemberService memberService;

    @GetMapping(value = "/google")
    public void socialLoginType() {
        oauthService.request();
    }

    @GetMapping(value = "/{socialLoginType}/token")
    public ResponseEntity<LoginResponseDto> callback(
            @PathVariable(name = "socialLoginType") String socialLoginType,
            @RequestParam(name = "code") String code) {
        final GoogleIdTokenVo googleIdTokenVo = oauthService.requestAccessToken(socialLoginType, code);

        if(!memberService.isMember(googleIdTokenVo.getEmail())) {
            return ResponseEntity.ok(memberService.signIn(googleIdTokenVo));
        }
        else
        {
            return ResponseEntity.ok(memberService.signUp(googleIdTokenVo));
        }
    }
}

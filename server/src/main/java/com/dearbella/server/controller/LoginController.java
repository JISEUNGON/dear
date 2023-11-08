package com.dearbella.server.controller;

import com.dearbella.server.service.oauth.OauthService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping(value = "/login")
@Api(tags = {"로그인 API"})
@RequiredArgsConstructor
public class LoginController {
    private final OauthService oauthService;

    @GetMapping(value = "/google")
    public void socialLoginType() {
        oauthService.request();
    }

    @GetMapping(value = "/{socialLoginType}/callback")
    public String callback(
            @PathVariable(name = "socialLoginType") String socialLoginType,
            @RequestParam(name = "code") String code) {
        return oauthService.requestAccessToken(socialLoginType, code);
    }
}

package com.dearbella.server.service.oauth;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class OauthService {
    private final HttpServletResponse response;

    public void request() {
        SocialOauth socialOauth = new GoogleOauth();
        String redirectURL = socialOauth.getOauthRedirectURL();
        try {
            response.sendRedirect(redirectURL);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String requestAccessToken(String socialLoginType, String code) {
        SocialOauth socialOauth = new GoogleOauth();
        return socialOauth.requestAccessToken(code);
    }
}
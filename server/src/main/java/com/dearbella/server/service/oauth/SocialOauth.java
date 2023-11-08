package com.dearbella.server.service.oauth;

public interface SocialOauth {
    String getOauthRedirectURL();

    String requestAccessToken(String code);
}
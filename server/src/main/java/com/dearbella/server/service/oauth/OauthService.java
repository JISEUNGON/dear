package com.dearbella.server.service.oauth;

import com.dearbella.server.dto.response.login.GoogleLoginResponse;
import com.dearbella.server.vo.GoogleIdTokenVo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class OauthService {
    private final HttpServletResponse response;
    @Value("${sns.google.client.id}")
    private String GOOGLE_SNS_CLIENT_ID;
    @Value("${sns.google.callback.url}")
    private String GOOGLE_SNS_CALLBACK_URL;
    @Value("${sns.google.client.secret}")
    private String GOOGLE_SNS_CLIENT_SECRET;

    public void request() {
        String redirectURL = getOauthRedirectURL();
        try {
            response.sendRedirect(redirectURL);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public GoogleIdTokenVo requestAccessToken(String type, String code) {
        String response = requestAccessToken(code);
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            //구글 로그인 응답
            final GoogleLoginResponse googleLoginResponse = objectMapper.readValue(response, GoogleLoginResponse.class);

            //아이디 토큰 객체화
            GoogleIdTokenVo idTokenVo = getIdTokenVo(googleLoginResponse.getId_token());

            return idTokenVo;
        } catch (JsonProcessingException e) {
            throw new RuntimeException("json failed");
        }
    }

    public String getOauthRedirectURL() {
        Map<String, Object> params = new HashMap<>();
        params.put("scope", "profile email openid");
        params.put("response_type", "code");
        params.put("client_id", GOOGLE_SNS_CLIENT_ID);
        params.put("redirect_uri", GOOGLE_SNS_CALLBACK_URL);

        String parameterString = params.entrySet().stream()
                .map(x -> x.getKey() + "=" + x.getValue())
                .collect(Collectors.joining("&"));

        return "https://accounts.google.com/o/oauth2/v2/auth" + "?" + parameterString;
    }

    private String requestAccessToken(String code) {
        RestTemplate restTemplate = new RestTemplate();

        Map<String, Object> params = new HashMap<>();
        params.put("code", code);
        params.put("client_id", GOOGLE_SNS_CLIENT_ID);
        params.put("client_secret", GOOGLE_SNS_CLIENT_SECRET);
        params.put("redirect_uri", GOOGLE_SNS_CALLBACK_URL);
        params.put("grant_type", "authorization_code");

        ResponseEntity<String> responseEntity =
                restTemplate.postForEntity("https://oauth2.googleapis.com/token", params, String.class);

        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            return responseEntity.getBody();
        }
        return "구글 로그인 요청 처리 실패";
    }

    private GoogleIdTokenVo getIdTokenVo(String idToken) throws JsonProcessingException {
        byte[] decode = Base64.decodeBase64(idToken.split("\\.")[1]);
        idToken = new String(decode, StandardCharsets.UTF_8);

        ObjectMapper objectMapper = new ObjectMapper();
        GoogleIdTokenVo googleIdTokenVo = objectMapper.readValue(idToken, GoogleIdTokenVo.class);

        return googleIdTokenVo;
    }
}
package com.dearbella.server.exception.token;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(HttpStatus.NOT_FOUND)
public class TokenNotFoundException extends RuntimeException {
    public TokenNotFoundException() {
        super("Token not found from accessToken and refreshToken");
    }
}
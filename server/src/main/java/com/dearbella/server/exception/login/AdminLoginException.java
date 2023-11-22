package com.dearbella.server.exception.login;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
@Getter
public class AdminLoginException extends RuntimeException {
    private String message;

    public AdminLoginException(String message) {
        super();
        this.message = message;
    }
}

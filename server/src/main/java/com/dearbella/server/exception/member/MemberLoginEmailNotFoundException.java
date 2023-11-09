package com.dearbella.server.exception.member;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
@Getter
public class MemberLoginEmailNotFoundException extends RuntimeException {
    private String message;

    public MemberLoginEmailNotFoundException(String email) {
        super(email);
        this.message = email;
    }
}

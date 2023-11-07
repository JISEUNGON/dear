package com.dearbella.server.exception.member;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(HttpStatus.NOT_FOUND)
public class MemberIdNotFoundException extends RuntimeException {
    private String message;
    public MemberIdNotFoundException(String id) {
        super(id);
        this.message = id;
    }
}

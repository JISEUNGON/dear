package com.dearbella.server.exception.banner;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
@Getter
public class BannerInfraNotFoundException extends RuntimeException {
    private String message;

    public BannerInfraNotFoundException(Long message) {
        super(message.toString());

        this.message = message.toString();
    }
}

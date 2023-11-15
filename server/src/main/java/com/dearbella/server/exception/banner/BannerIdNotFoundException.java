package com.dearbella.server.exception.banner;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
@Getter
public class BannerIdNotFoundException extends RuntimeException {
    private String message;

    public BannerIdNotFoundException(Long id) {
        super(id.toString());
        message = id.toString();
    }
}

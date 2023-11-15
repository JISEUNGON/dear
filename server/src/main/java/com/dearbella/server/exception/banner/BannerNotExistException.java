package com.dearbella.server.exception.banner;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
@Getter
public class BannerNotExistException extends RuntimeException {
    private final String message = "There are not banners";

    public BannerNotExistException() {
        super();
    }
}

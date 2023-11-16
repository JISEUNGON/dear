package com.dearbella.server.exception.review;

import lombok.Getter;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus
@Getter
public class ReviewNotFoundException extends RuntimeException {
    private final String message = "No Review Exist.";

    public ReviewNotFoundException() {
        super();
    }
}

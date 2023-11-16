package com.dearbella.server.exception.review;

import lombok.Getter;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus
@Getter
public class ReviewIdNotFoundException extends RuntimeException {
    private String message;

    public ReviewIdNotFoundException(Long id) {
        super(id.toString());
        this.message = id.toString();
    }
}

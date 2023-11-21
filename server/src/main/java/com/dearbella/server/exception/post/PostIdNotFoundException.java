package com.dearbella.server.exception.post;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
@Getter
public class PostIdNotFoundException extends RuntimeException {
    private String message;
    public PostIdNotFoundException(final Long postId) {
        super(postId.toString());
        this.message = postId.toString();
    }
}

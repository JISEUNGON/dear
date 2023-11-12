package com.dearbella.server.exception.doctor;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
@Getter
public class CategoryNotFoundException extends RuntimeException {
    private String message;

    public CategoryNotFoundException(Long tag) {
        super(tag.toString());
        this.message = tag.toString();
    }
}

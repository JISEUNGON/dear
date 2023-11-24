package com.dearbella.server.exception.image;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
@Getter
public class FileNameNotValidException extends RuntimeException {
    private final String message = "File name has alphabet or number.";

    public FileNameNotValidException() {
        super();
    }
}

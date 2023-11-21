package com.dearbella.server.exception.inquiry;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
@Getter
public class InquiryIdNotFoundException extends RuntimeException {
    private String message;

    public InquiryIdNotFoundException(final Long id) {
        super(id.toString());
        message = id.toString();
    }
}

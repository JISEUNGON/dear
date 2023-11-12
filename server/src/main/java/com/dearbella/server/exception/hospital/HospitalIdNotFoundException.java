package com.dearbella.server.exception.hospital;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(HttpStatus.NOT_FOUND)
public class HospitalIdNotFoundException extends RuntimeException {
    private String message;

    public HospitalIdNotFoundException(Long hospitalId) {
        super(hospitalId.toString());
        this.message = hospitalId.toString();
    }
}

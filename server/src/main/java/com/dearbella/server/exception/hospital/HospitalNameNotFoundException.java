package com.dearbella.server.exception.hospital;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
@Getter
public class HospitalNameNotFoundException extends RuntimeException {
    private String message;

    public HospitalNameNotFoundException(final String hospitalName) {
        super(hospitalName);
        message = hospitalName;
    }
}

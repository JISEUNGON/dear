package com.dearbella.server.exception.hospital;

import lombok.Getter;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus
@Getter
public class HospitalResponseNullException extends RuntimeException {
    private final String message = "Hospital Response is not exist. No Error";

    public HospitalResponseNullException() {
        super();
    }
}

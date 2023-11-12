package com.dearbella.server.exception.doctor;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
@Getter
public class DoctorIdNotFoundException extends RuntimeException {
    private String message;

    public DoctorIdNotFoundException(Long doctorId) {
        super(doctorId.toString());
        this.message = doctorId.toString();
    }
}

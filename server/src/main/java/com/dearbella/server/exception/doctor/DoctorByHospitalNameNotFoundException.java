package com.dearbella.server.exception.doctor;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
@Getter
public class DoctorByHospitalNameNotFoundException extends RuntimeException {
    private String message;
    
    public DoctorByHospitalNameNotFoundException(String name) {
        super(name);
        message = name;
    }
}
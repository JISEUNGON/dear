package com.dearbella.server.exception;

import com.dearbella.server.exception.member.MemberIdNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ApiExceptionHandler {
    /**
     * User Error DEU => DearBella User Error
     * */

    @ExceptionHandler(MemberIdNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleException(MemberIdNotFoundException ex) {
        ApiErrorResponse response = new ApiErrorResponse("DEM-0001","user is not found - id "+ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    /**
     * Unknown Error
     * */
}

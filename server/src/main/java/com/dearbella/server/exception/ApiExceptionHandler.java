package com.dearbella.server.exception;

import com.dearbella.server.exception.banner.BannerInfraNotFoundException;
import com.dearbella.server.exception.doctor.CategoryNotFoundException;
import com.dearbella.server.exception.member.MemberIdNotFoundException;
import com.dearbella.server.exception.member.MemberLoginEmailNotFoundException;
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

    @ExceptionHandler(MemberLoginEmailNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleException(MemberLoginEmailNotFoundException ex) {
        ApiErrorResponse response = new ApiErrorResponse("DEM-0002","Email is not found - id "+ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    /**
     * Banner Error
     * */
    @ExceptionHandler(BannerInfraNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleException(BannerInfraNotFoundException ex) {
        ApiErrorResponse response = new ApiErrorResponse("DEB-001", "banner tag is not found: " + ex.getMessage());

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    /**
     * doctor error
     * */
    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleException(CategoryNotFoundException ex) {
        ApiErrorResponse response = new ApiErrorResponse("DED-001", "Doctor tag is not exist: " + ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    /**
     * Unknown Error
     * */
}

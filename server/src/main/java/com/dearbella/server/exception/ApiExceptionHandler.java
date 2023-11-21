package com.dearbella.server.exception;

import com.dearbella.server.exception.banner.BannerIdNotFoundException;
import com.dearbella.server.exception.banner.BannerInfraNotFoundException;
import com.dearbella.server.exception.banner.BannerNotExistException;
import com.dearbella.server.exception.doctor.CategoryNotFoundException;
import com.dearbella.server.exception.doctor.DoctorByHospitalNameNotFoundException;
import com.dearbella.server.exception.doctor.DoctorIdNotFoundException;
import com.dearbella.server.exception.hospital.HospitalIdNotFoundException;
import com.dearbella.server.exception.hospital.HospitalResponseNullException;
import com.dearbella.server.exception.inquiry.InquiryIdNotFoundException;
import com.dearbella.server.exception.member.MemberIdNotFoundException;
import com.dearbella.server.exception.member.MemberLoginEmailNotFoundException;
import com.dearbella.server.exception.post.PostIdNotFoundException;
import com.dearbella.server.exception.post.TagIdNotFoundException;
import com.dearbella.server.exception.review.ReviewIdNotFoundException;
import com.dearbella.server.exception.review.ReviewNotFoundException;
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

    @ExceptionHandler(BannerNotExistException.class)
    public ResponseEntity<ApiErrorResponse> handleException(BannerNotExistException ex) {
        ApiErrorResponse response = new ApiErrorResponse("DEB-002", ex.getMessage());

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BannerIdNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleException(BannerIdNotFoundException ex) {
        ApiErrorResponse response = new ApiErrorResponse("DEB-003", ex.getMessage());

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

    @ExceptionHandler(DoctorIdNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleException(DoctorIdNotFoundException ex) {
        ApiErrorResponse response = new ApiErrorResponse("DED-002", "Doctor id is not exist: " + ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DoctorByHospitalNameNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleException(DoctorByHospitalNameNotFoundException ex) {
        ApiErrorResponse response = new ApiErrorResponse("DED-003", "Doctor is not exist by hospital name: " + ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    /**
     * hospital error
     * */
    @ExceptionHandler(HospitalIdNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleException(HospitalIdNotFoundException ex) {
        ApiErrorResponse response = new ApiErrorResponse("DEH-001", "HospitalController id is not exist: " + ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(HospitalResponseNullException.class)
    public ResponseEntity<ApiErrorResponse> handleException(HospitalResponseNullException ex) {
        ApiErrorResponse response = new ApiErrorResponse("DEH-002", ex.getMessage());

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    /**
     * post Error
     * */
    @ExceptionHandler(TagIdNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleException(TagIdNotFoundException ex) {
        ApiErrorResponse response = new ApiErrorResponse("DEP-001", "Tag id is not exist: " + ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PostIdNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleException(PostIdNotFoundException ex)  {
        ApiErrorResponse response = new ApiErrorResponse("DEP-002", "Post id not found: " + ex.getMessage());

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    /**
     * Review Error
     * */
    @ExceptionHandler(ReviewNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleException(ReviewNotFoundException ex) {
        ApiErrorResponse response = new ApiErrorResponse("DER-001", ex.getMessage());

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ReviewIdNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleException(ReviewIdNotFoundException ex) {
        ApiErrorResponse response = new ApiErrorResponse("DER-0002", "Review id not exist: " + ex.getMessage());

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    /**
     * inquiry Exception
     * */
    @ExceptionHandler(InquiryIdNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleException(InquiryIdNotFoundException ex) {
        ApiErrorResponse response = new ApiErrorResponse("DEI-001", "Inquiry id is not exist: id " + ex.getMessage());

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}

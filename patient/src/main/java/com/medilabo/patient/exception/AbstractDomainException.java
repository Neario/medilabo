package com.medilabo.patient.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class AbstractDomainException extends RuntimeException{

    protected HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;

    protected AbstractDomainException(String message) {
        super(message);
    }

    protected AbstractDomainException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

}

package com.medilabo.auth.exception;

import org.springframework.http.HttpStatus;

public class UnauthorizedException extends AbstractDomainException{
    public UnauthorizedException(String message){
        super(message, HttpStatus.UNAUTHORIZED);
    }
}

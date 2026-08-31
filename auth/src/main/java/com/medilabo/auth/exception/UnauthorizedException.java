package com.medilabo.auth.exception;

import org.springframework.http.HttpStatus;

/**
 * Thrown when login credentials are invalid, with {@code 401 Unauthorized}.
 */
public class UnauthorizedException extends AbstractDomainException{

    /**
     * @param message detail message returned to the client
     */
    public UnauthorizedException(String message){
        super(message, HttpStatus.UNAUTHORIZED);
    }
}

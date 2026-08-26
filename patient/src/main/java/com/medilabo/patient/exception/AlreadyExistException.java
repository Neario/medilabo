package com.medilabo.patient.exception;

import org.springframework.http.HttpStatus;

/**
 * throw AlreadyExistException with {@code 409 Conflict}
 */
public class AlreadyExistException extends AbstractDomainException{

    public AlreadyExistException(String simpleName){
        super("Resource " + simpleName + " already exist", HttpStatus.CONFLICT);
    }
}

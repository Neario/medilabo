package com.medilabo.patient.exception;

import org.springframework.http.HttpStatus;

public class AlreadyExistException extends AbstractDomainException{
    public AlreadyExistException(String simpleName){
        super("Resource " + simpleName + " already exist", HttpStatus.CONFLICT);
    }
}

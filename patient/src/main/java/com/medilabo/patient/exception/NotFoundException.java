package com.medilabo.patient.exception;

import org.springframework.http.HttpStatus;

public class NotFoundException extends AbstractDomainException{
    public NotFoundException(String simpleName, Long id){
        super("Resource '" + simpleName + "' not found with id = '" + id + "'", HttpStatus.NOT_FOUND);
    }
}

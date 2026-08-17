package com.medilabo.auth.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AbstractDomainException.class)
    public ResponseEntity<?> handleAbstractDomainException(final AbstractDomainException abstractDomainException) {
        return ResponseEntity.status(abstractDomainException.getHttpStatus()).body(abstractDomainException.getMessage());
    }
}

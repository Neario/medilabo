package com.medilabo.patient.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Handle HTTP exception , centralizes controller errors and applies a response based on the returned exception.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handle all {@link AbstractDomainException} for give a generic response in abstractDomainException
     *
     * @param abstractDomainException raised with layer logic
     * @return an error response with the exception's status and message as body
     */
    @ExceptionHandler(AbstractDomainException.class)
    public ResponseEntity<?> handleAbstractDomainException(final AbstractDomainException abstractDomainException) {
        return ResponseEntity.status(abstractDomainException.getHttpStatus()).body(abstractDomainException.getMessage());
    }
}

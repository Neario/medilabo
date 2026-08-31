package com.medilabo.auth.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Handle HTTP exception , centralizes controller errors and applies a response based on the returned exception.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles every {@link AbstractDomainException}, using the exception's own status and message to build the response.
     *
     * @param abstractDomainException raised with layer logic
     * @return an error response with the exception's status and message as body
     */
    @ExceptionHandler(AbstractDomainException.class)
    public ResponseEntity<?> handleAbstractDomainException(final AbstractDomainException abstractDomainException) {
        return ResponseEntity.status(abstractDomainException.getHttpStatus()).body(abstractDomainException.getMessage());
    }
}

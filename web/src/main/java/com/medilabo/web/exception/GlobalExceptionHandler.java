package com.medilabo.web.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;

/**
 * Handle HTTP exception , centralizes controller errors and applies a response based on the returned exception
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles a {@code 404 Not Found} and rendering an error page
     *
     * @param e the exception raised
     * @param request the incoming request, used to extract the id from the URI
     * @param model the view model, populated with the extracted id
     * @return the {@code errors/404} view name
     */
    @ExceptionHandler(HttpClientErrorException.NotFound.class)
    public String handleNotFound(HttpClientErrorException.NotFound e, HttpServletRequest request, Model model) {
        String uri = request.getRequestURI();
        String id = uri.substring(uri.lastIndexOf('/') + 1);
        model.addAttribute("id", id);
        return "errors/404";
    }
}

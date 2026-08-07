package com.medilabo.web.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(HttpClientErrorException.NotFound.class)
    public String handleNotFound(HttpClientErrorException.NotFound e, HttpServletRequest request, Model model) {
        String uri = request.getRequestURI();
        String id = uri.substring(uri.lastIndexOf('/') + 1);
        model.addAttribute("id", id);
        return "errors/404";
    }
}

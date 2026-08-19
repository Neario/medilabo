package com.medilabo.web.controller;

import com.medilabo.web.dto.LoginRequestDTO;
import com.medilabo.web.service.interfaces.LoginService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.HttpClientErrorException;

import java.time.Duration;

@Controller
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute @Valid LoginRequestDTO loginRequestDTO, Model model, HttpServletResponse response) {
        try {
            String token = loginService.login(loginRequestDTO);

            ResponseCookie cookie = ResponseCookie.from("jwt", token)
                    .httpOnly(true)
                    .path("/")
                    .maxAge(Duration.ofHours(1))
                    .build();
            response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

            return "redirect:/patients";
        } catch (HttpClientErrorException.Unauthorized e ) {
            model.addAttribute("error", true);
            return "login";
        }
    }

    @PostMapping("/logout")
    public String logout(HttpServletResponse response) {
        ResponseCookie cookie = ResponseCookie.from("jwt", "")
                .httpOnly(true)
                .path("/")
                .maxAge(Duration.ZERO)
                .build();
        System.out.println(cookie);
        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
        return "redirect:/login";
    }
}

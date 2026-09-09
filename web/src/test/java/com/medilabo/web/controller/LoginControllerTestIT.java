package com.medilabo.web.controller;

import com.medilabo.web.client.AuthClient;
import com.medilabo.web.dto.LoginRequestDTO;
import com.medilabo.web.dto.LoginResponseDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.HttpClientErrorException;

import java.nio.charset.StandardCharsets;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
public class LoginControllerTestIT {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AuthClient authClient;

    @Test
    public void should_return_login_view() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"));
    }

    @Test
    public void should_set_cookie_and_redirect_on_valid_credentials() throws Exception {
        when(authClient.login(any(LoginRequestDTO.class))).thenReturn(new LoginResponseDTO("signed-jwt-token"));

        mockMvc.perform(post("/login")
                        .with(csrf())
                        .param("identifier", "Mika")
                        .param("password", "password123"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/patients"))
                .andExpect(cookie().value("jwt", "signed-jwt-token"))
                .andExpect(cookie().httpOnly("jwt", true));
    }

    @Test
    public void should_return_login_view_with_error_on_bad_credentials() throws Exception {
        when(authClient.login(any(LoginRequestDTO.class)))
                .thenThrow(HttpClientErrorException.create(
                        HttpStatusCode.valueOf(401),
                        "Unauthorized",HttpHeaders.EMPTY,
                        new byte[0],
                        StandardCharsets.UTF_8)
                );

        mockMvc.perform(post("/login")
                        .with(csrf())
                        .param("identifier", "Mika")
                        .param("password", "badCredentials"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"))
                .andExpect(model().attribute("error", true));
    }

    @Test
    @WithMockUser
    public void should_clear_cookie_and_redirect_on_logout() throws Exception {
        mockMvc.perform(post("/logout")
                        .with(csrf())
                ).andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));
    }
}

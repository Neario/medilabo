package com.medilabo.auth.controller;

import com.medilabo.auth.dto.LoginRequest;
import com.medilabo.auth.model.User;
import com.medilabo.auth.model.enumerations.Role;
import com.medilabo.auth.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.ObjectWriter;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@Transactional
public class AuthControllerTestIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    private final ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();

    @BeforeEach
    public void setUp() {
        User user = new User();
        user.setIdentifier("Mika");
        user.setPassword(passwordEncoder.encode("password123"));
        user.setRole(Role.ORGANIZER);
        userRepository.save(user);
    }

    @Test
    public void should_return_token_on_valid_credentials() throws Exception {
        LoginRequest loginRequest = new LoginRequest("Mika", "password123");

        mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectWriter.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").isNotEmpty());
    }

    @Test
    public void should_return_unauthorized_with_bad_credentials() throws Exception {
        LoginRequest loginRequest = new LoginRequest("Mika", "badCredentials");

        mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectWriter.writeValueAsString(loginRequest)))
                .andExpect(status().isUnauthorized());
    }
}

package com.medilabo.web.service;

import com.medilabo.web.client.AuthClient;
import com.medilabo.web.dto.LoginRequestDTO;
import com.medilabo.web.dto.LoginResponseDTO;
import com.medilabo.web.service.interfaces.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {

    private final AuthClient authClient;

    @Override
    public String login(LoginRequestDTO loginRequestDTO) {
        LoginResponseDTO loginResponse = authClient.login(loginRequestDTO);
        return loginResponse.token();
    }
}

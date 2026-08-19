package com.medilabo.web.service.interfaces;

import com.medilabo.web.dto.LoginRequestDTO;

public interface LoginService {
    String login(LoginRequestDTO loginRequestDTO);
}

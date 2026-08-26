package com.medilabo.web.service.interfaces;

import com.medilabo.web.dto.LoginRequestDTO;

/**
 * Authenticates a user
 */
public interface LoginService {

    /**
     * @param loginRequestDTO the submitted credentials
     * @return the signed JWT to store in the client's cookie
     * @throws org.springframework.web.client.HttpClientErrorException.Unauthorized if the credentials are invalid
     */
    String login(LoginRequestDTO loginRequestDTO);
}

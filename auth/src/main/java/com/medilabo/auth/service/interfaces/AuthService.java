package com.medilabo.auth.service.interfaces;

/**
 * Authenticates users and issues access tokens.
 */
public interface AuthService {

    /**
     * Verifies credentials and issues a JWT.
     *
     * @param identifier user's login identifier
     * @param password user's clear-text password
     * @return a signed JWT for this user
     * @throws com.medilabo.auth.exception.UnauthorizedException if the credentials are invalid
     */
    String login(String identifier, String password);
}

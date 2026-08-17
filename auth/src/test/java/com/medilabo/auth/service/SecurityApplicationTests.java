package com.medilabo.auth.service;

import com.medilabo.auth.model.User;
import com.medilabo.auth.model.enumerations.Role;
import com.medilabo.auth.security.JwtService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class SecurityApplicationTests {

    @Autowired
    private JwtService jwtService;

    @Test
    public void testGenerateToken() {
        User user = new User();
        user.setIdentifier("Mika");
        user.setPassword("Mika");
        user.setRole(Role.ORGANIZER);
        user.setId(4L);

        String token = jwtService.generateAccessToken(user);
        System.out.println("Generated Token: " + token);

        Assertions.assertNotNull(token);
        Assertions.assertFalse(token.isEmpty());

        Long extractedId = jwtService.getUserIdFromToken(token);
        System.out.println("Extracted User ID: " + extractedId);
        Assertions.assertEquals(user.getId(),extractedId);
    }
}

package com.cts.backend.elibrary.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import com.cts.backend.elibrary.dto.LoginDto;
import com.cts.backend.elibrary.security.JwtTokenProvider;

public class AuthServiceImplTest {

	@Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @InjectMocks
    private AuthServiceImpl authService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
//        authService = new AuthServiceImpl(authenticationManager, jwtTokenProvider);
    }

    @Test
    void testLogin() {
        // Arrange
        LoginDto loginDto = new LoginDto();
        loginDto.setUsername("john");
        loginDto.setPassword("password");

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                loginDto.getUsername(), loginDto.getPassword());

        Authentication authentication = mock(Authentication.class);
        when(authenticationManager.authenticate(authenticationToken)).thenReturn(authentication);

        String expectedToken = "jwt.token";
        when(jwtTokenProvider.generateToken(authentication)).thenReturn(expectedToken);

        // Act
        String token = authService.login(loginDto);

        // Assert
        assertEquals(expectedToken, token);
        verify(authenticationManager).authenticate(authenticationToken);
        verify(jwtTokenProvider).generateToken(authentication);
    }
}

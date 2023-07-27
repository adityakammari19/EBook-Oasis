package com.cts.backend.elibrary.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.cts.backend.elibrary.dto.AuthResponse;
import com.cts.backend.elibrary.dto.LoginDto;
import com.cts.backend.elibrary.service.AuthService;

public class AuthControllerTest {

	@Mock
    private AuthService authService;

	@InjectMocks
    private AuthController authController;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLogin() {
        // Arrange
        LoginDto loginDto = new LoginDto();
        String token = "testToken";
        when(authService.login(loginDto)).thenReturn(token);

        // Act
        ResponseEntity<AuthResponse> response = authController.login(loginDto);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(token, response.getBody().getAccessToken());
        verify(authService).login(loginDto);
    }
}

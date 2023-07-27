package com.cts.backend.elibrary.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.cts.backend.elibrary.exception.ConflictException;
import com.cts.backend.elibrary.exception.UserNotFoundException;
import com.cts.backend.elibrary.model.User;
import com.cts.backend.elibrary.service.impl.UserServiceImpl;

public class UserControllerTest {

	@Mock
    private UserServiceImpl userService;

    private UserController userController;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        userController = new UserController(userService);
    }

    @Test
    void testRegisterUser() throws ConflictException {
        // Arrange
        User user = new User();
        User savedUser = new User();
        when(userService.saveUser(user)).thenReturn(savedUser);

        // Act
        ResponseEntity<User> response = userController.registerUser(user);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(savedUser, response.getBody());
        verify(userService).saveUser(user);
    }

    @Test
    void testGetAllUsers() {
        // Arrange
        List<User> expectedUsers = Arrays.asList(new User(), new User());
        when(userService.getAllUsers()).thenReturn(expectedUsers);

        // Act
        ResponseEntity<List<User>> response = userController.getAllUsers();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedUsers, response.getBody());
        verify(userService).getAllUsers();
    }

    @Test
    void testGetUserById() throws UserNotFoundException {
        // Arrange
        Long id = 1L;
        User expectedUser = new User();
        when(userService.getUserById(id)).thenReturn(expectedUser);

        // Act
        ResponseEntity<User> response = userController.getUserById(id);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedUser, response.getBody());
        verify(userService).getUserById(id);
    }
}

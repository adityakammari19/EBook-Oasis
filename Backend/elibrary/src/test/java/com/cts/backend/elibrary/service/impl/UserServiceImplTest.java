package com.cts.backend.elibrary.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.cts.backend.elibrary.exception.ConflictException;
import com.cts.backend.elibrary.exception.UserNotFoundException;
import com.cts.backend.elibrary.model.Role;
import com.cts.backend.elibrary.model.User;
import com.cts.backend.elibrary.repository.RoleRepository;
import com.cts.backend.elibrary.repository.UserRepository;

public class UserServiceImplTest {

	@Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    private UserServiceImpl userService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        userService = new UserServiceImpl(userRepository, roleRepository, passwordEncoder);
    }

    @Test
    void testGetUserByUsername_Found() throws UserNotFoundException {
        // Arrange
        String username = "john123";
        User expectedUser = new User();
        when(userRepository.findUserByUsername(username)).thenReturn(expectedUser);

        // Act
        User result = userService.getUserByUsername(username);

        // Assert
        assertEquals(expectedUser, result);
    }

    @Test
    void testGetUserByUsername_NotFound() {
        // Arrange
        String username = "john123";
        when(userRepository.findUserByUsername(username)).thenReturn(null);

        // Act and Assert
        assertThrows(UserNotFoundException.class, () -> userService.getUserByUsername(username));
    }

    @Test
    void testSaveUser() throws ConflictException {
        // Arrange
    	Role role = new Role();
    	role.setName("USER");
        User user = new User();
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setUserId(1L);
        user.setUsername("john");
        user.setEmail("john@gmail.com");
        user.setPassword("password");
        user.setPhoneNumber("9876543210");
        user.setEnabled(true);
        Set<Role> roles = new HashSet<>(Arrays.asList(role));
        user.setRoles(roles);
        when(userRepository.findUserByUsername(user.getUsername())).thenReturn(null);
        when(roleRepository.findByName("USER")).thenReturn(role);
        when(userRepository.save(any(User.class))).thenReturn(user);

        // Act
        User result = userService.saveUser(user);

        // Assert
        assertEquals(user, result);
        assertTrue(result.isEnabled());
        assertEquals(roles, result.getRoles());
        verify(passwordEncoder).encode(user.getPassword());
    }

    @Test
    void testSaveUser_ConflictException() {
        // Arrange
        User user = new User();
        user.setUsername("john123");
        when(userRepository.findUserByUsername(user.getUsername())).thenReturn(new User());

        // Act and Assert
        assertThrows(ConflictException.class, () -> userService.saveUser(user));
    }

    @Test
    void testGetAllUsers() {
        // Arrange
        List<User> expectedUsers = Arrays.asList(new User(), new User());
        when(userRepository.findAll()).thenReturn(expectedUsers);

        // Act
        List<User> result = userService.getAllUsers();

        // Assert
        assertEquals(expectedUsers, result);
    }

    @Test
    void testGetUserById_Found() throws UserNotFoundException {
        // Arrange
        Long userId = 1L;
        User expectedUser = new User();
        when(userRepository.findByUserId(userId)).thenReturn(expectedUser);

        // Act
        User result = userService.getUserById(userId);

        // Assert
        assertEquals(expectedUser, result);
    }

    @Test
    void testGetUserById_NotFound() {
        // Arrange
        Long userId = 1L;
        when(userRepository.findByUserId(userId)).thenReturn(null);

        // Act and Assert
        assertThrows(UserNotFoundException.class, () -> userService.getUserById(userId));
    }
}

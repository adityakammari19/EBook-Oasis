package com.cts.backend.elibrary.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.cts.backend.elibrary.model.User;

@DataJpaTest
public class UserRepositoryTest {

	@Autowired
    private UserRepository userRepository;

    private User user;

    @BeforeEach
    public void setup() {
        user = new User();
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setUserId(1L);
        user.setUsername("john");
        user.setEmail("john@gmail.com");
        user.setPassword("password");
        user.setPhoneNumber("9876543210");

        userRepository.save(user);
    }

    @AfterEach
    public void teardown() {
        userRepository.deleteAll();
    }

    @Test
    public void testFindUserByUsername_Found() {
        // Arrange
        String username = "john";

        // Act
        User foundUser = userRepository.findUserByUsername(username);

        // Assert
        assertNotNull(foundUser);
        assertEquals(username, foundUser.getUsername());
    }

    @Test
    public void testFindUserByUsername_NotFound() {
        // Arrange
        String username = "nonexistent.user";

        // Act
        User foundUser = userRepository.findUserByUsername(username);

        // Assert
        assertNull(foundUser);
    }

    @Test
    public void testDeleteUserByUserId() {
        // Arrange
        long userId = user.getUserId();

        // Act
        userRepository.deleteUserByUserId(userId);

        // Assert
        Optional<User> deletedUser = userRepository.findById(userId);
        assertEquals(Optional.empty(), deletedUser);
    }

    @Test
    public void testFindByUserId_Found() {
        // Arrange
        long userId = user.getUserId();
        // Act
        User foundUser = userRepository.findByUserId(userId);
        // Assert
        assertNotNull(foundUser);
        assertEquals(userId, foundUser.getUserId());
    }

    @Test
    public void testFindByUserId_NotFound() {
        // Arrange
        long nonExistingUserId = 999L;

        // Act
        User foundUser = userRepository.findByUserId(nonExistingUserId);

        // Assert
        assertNull(foundUser);
    }

    @Test
    public void testFindByUsername_Found() {
        // Arrange
        String username = "john";

        // Act
        Optional<User> foundUser = userRepository.findByUsername(username);

        // Assert
        assertTrue(foundUser.isPresent());
        assertEquals(username, foundUser.get().getUsername());
    }

    @Test
    public void testFindByUsername_NotFound() {
        // Arrange
        String nonExistingUsername = "nonexistent.user";

        // Act
        Optional<User> foundUser = userRepository.findByUsername(nonExistingUsername);

        // Assert
        assertFalse(foundUser.isPresent());
    }

    @Test
    public void testFindByEmail_Found() {
        // Arrange
        String email = "john@gmail.com";

        // Act
        Optional<User> foundUser = userRepository.findByEmail(email);

        // Assert
        assertTrue(foundUser.isPresent());
        assertEquals(email, foundUser.get().getEmail());
    }

    @Test
    public void testFindByEmail_NotFound() {
        // Arrange
        String nonExistingEmail = "nonexistent.email@example.com";

        // Act
        Optional<User> foundUser = userRepository.findByEmail(nonExistingEmail);

        // Assert
        assertFalse(foundUser.isPresent());
    }

    @Test
    public void testFindByUsernameOrEmail_FoundByUsername() {
        // Arrange
        String username = "john";

        // Act
        Optional<User> foundUser = userRepository.findByUsernameOrEmail(username, "");

        // Assert
        assertTrue(foundUser.isPresent());
        assertEquals(username, foundUser.get().getUsername());
    }

    @Test
    public void testFindByUsernameOrEmail_FoundByEmail() {
        // Arrange
        String email = "john@gmail.com";

        // Act
        Optional<User> foundUser = userRepository.findByUsernameOrEmail("", email);

        // Assert
        assertTrue(foundUser.isPresent());
        assertEquals(email, foundUser.get().getEmail());
    }

    @Test
    public void testFindByUsernameOrEmail_NotFound() {
        // Arrange
        String nonExistingUsername = "nonexistent.user";
        String nonExistingEmail = "nonexistent.email@example.com";

        // Act
        Optional<User> foundUser = userRepository.findByUsernameOrEmail(nonExistingUsername, nonExistingEmail);

        // Assert
        assertFalse(foundUser.isPresent());
    }
}

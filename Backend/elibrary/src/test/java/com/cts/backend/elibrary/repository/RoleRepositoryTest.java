package com.cts.backend.elibrary.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.cts.backend.elibrary.model.Role;

@DataJpaTest
public class RoleRepositoryTest {

	@Autowired
    private RoleRepository roleRepository;

    private Role role;

    @BeforeEach
    public void setup() {
        role = new Role();
        role.setId(1L);
        role.setName("ADMIN");

        roleRepository.save(role);
    }

    @AfterEach
    public void teardown() {
        roleRepository.deleteAll();
    }

    @Test
    public void testFindByName_Found() {
        // Arrange
        String roleName = "ADMIN";

        // Act
        Role foundRole = roleRepository.findByName(roleName);

        // Assert
        assertNotNull(foundRole);
        assertEquals(roleName, foundRole.getName());
    }

    @Test
    public void testFindByName_NotFound() {
        // Arrange
        String roleName = "NONEXISTENT_ROLE";

        // Act
        Role foundRole = roleRepository.findByName(roleName);

        // Assert
        assertNull(foundRole);
    }
}

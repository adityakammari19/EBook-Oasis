package com.cts.backend.elibrary.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.cts.backend.elibrary.dto.RoleDto;
import com.cts.backend.elibrary.model.Role;
import com.cts.backend.elibrary.service.RoleService;

public class RoleControllerTest {

	@Mock
    private RoleService roleService;

    private RoleController roleController;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        roleController = new RoleController(roleService);
    }

    @Test
    void testCreateRole() {
        // Arrange
        RoleDto roleDto = new RoleDto();
        Role savedRole = new Role();
        when(roleService.createRole(roleDto)).thenReturn(savedRole);

        // Act
        ResponseEntity<Role> response = roleController.createRole(roleDto);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(savedRole, response.getBody());
        verify(roleService).createRole(roleDto);
    }
}

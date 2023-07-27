package com.cts.backend.elibrary.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.cts.backend.elibrary.dto.RoleDto;
import com.cts.backend.elibrary.model.Role;
import com.cts.backend.elibrary.repository.RoleRepository;

public class RoleServiceImplTest {

	@Mock
    private RoleRepository roleRepository;

    private RoleServiceImpl roleService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        roleService = new RoleServiceImpl(roleRepository);
    }

    @Test
    void testGetRoleByName() {
        // Arrange
        String roleName = "USER";
        Role expectedRole = new Role();
        when(roleRepository.findByName(roleName)).thenReturn(expectedRole);

        // Act
        Role result = roleService.getRoleByName(roleName);

        // Assert
        assertEquals(expectedRole, result);
    }

    @Test
    void testCreateRole() {
        // Arrange
        RoleDto roleDto = new RoleDto();
       roleDto.setId(1L);
        roleDto.setName("ADMIN");
        Role role = new Role();
        role.setId(roleDto.getId());
        role.setName(roleDto.getName());
        when(roleRepository.save(any(Role.class))).thenReturn(role);

        // Act
        Role result = roleService.createRole(roleDto);

        // Assert
        assertEquals(role, result);
//        verify(roleRepository).save(role);
    }
}

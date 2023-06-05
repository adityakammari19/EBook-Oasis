package com.cts.backend.elibrary.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cts.backend.elibrary.dto.RoleDto;
import com.cts.backend.elibrary.model.Role;
import com.cts.backend.elibrary.service.RoleService;

@RestController 
@RequestMapping("/api/roles") 
public class RoleController {

	@Autowired
	private final RoleService roleService; 

 
    public RoleController(RoleService roleService) { 
        this.roleService = roleService; 
    } 

    @PostMapping 
    public ResponseEntity<Role> createRole(@RequestBody RoleDto roleDto) { 
        Role savedRole = roleService.createRole(roleDto); 
        return ResponseEntity.status(HttpStatus.CREATED).body(savedRole); 
    } 
}

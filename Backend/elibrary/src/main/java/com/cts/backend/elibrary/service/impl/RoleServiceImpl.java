package com.cts.backend.elibrary.service.impl
;

import org.springframework.stereotype.Service;

import com.cts.backend.elibrary.dto.RoleDto;
import com.cts.backend.elibrary.model.Role;
import com.cts.backend.elibrary.repository.RoleRepository;
import com.cts.backend.elibrary.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService{

	 private final RoleRepository roleRepository; 

	    public RoleServiceImpl(RoleRepository roleRepository) { 
	        this.roleRepository = roleRepository; 
	    } 

	    public Role getRoleByName(String roleName) { 
	        return roleRepository.findByName(roleName); 
	    } 

	    public Role createRole(RoleDto roleDto) { 
	        Role role = new Role(); 
	        role.setId(roleDto.getId());
	        role.setName(roleDto.getName()); 
	        return roleRepository.save(role); 
	    } 
}

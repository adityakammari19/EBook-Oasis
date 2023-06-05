package com.cts.backend.elibrary.service;

import com.cts.backend.elibrary.dto.RoleDto;
import com.cts.backend.elibrary.model.Role;

public interface RoleService {

	public Role getRoleByName(String roleName) ;

    public Role createRole(RoleDto roleDto) ;

}

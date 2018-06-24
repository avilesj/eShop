package com.hercule.eshop.services;

import java.util.HashSet;

import com.hercule.eshop.models.Role;

public interface RoleService {

	public void saveRole(Role role);
	public HashSet<Role> getAllRoles();
	
}

package com.hercule.eshop.services;

import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;

import com.hercule.eshop.models.Role;
import com.hercule.eshop.repositories.RoleRepository;

public interface RoleService {

	public void saveRole(Role role);
	public HashSet<Role> getAllRoles();
	
}

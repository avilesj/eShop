package com.javiles.eshop.services;

import com.javiles.eshop.models.Role;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;

@Transactional
@Service
public interface RoleService
{

    void saveRole(Role role);

    HashSet<Role> getAllRoles();

    Role findRoleByName(String name);

    void deleteRole(Role role);

}

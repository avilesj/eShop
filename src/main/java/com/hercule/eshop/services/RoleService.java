package com.hercule.eshop.services;

import com.hercule.eshop.models.Role;

import java.util.HashSet;

public interface RoleService
{

    void saveRole(Role role);

    HashSet<Role> getAllRoles();

    Role findRoleByName(String name);

    void deleteRole(Role role);

}

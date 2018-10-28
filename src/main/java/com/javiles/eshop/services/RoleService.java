package com.javiles.eshop.services;

import com.javiles.eshop.models.Role;

import java.util.HashSet;

public interface RoleService
{

    void saveRole(Role role);

    HashSet<Role> getAllRoles();

    Role findRoleByName(String name);

    void deleteRole(Role role);

}

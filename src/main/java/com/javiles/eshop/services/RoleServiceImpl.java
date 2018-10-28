package com.javiles.eshop.services;

import com.javiles.eshop.models.Role;
import com.javiles.eshop.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;

public class RoleServiceImpl implements RoleService
{

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void saveRole(Role role)
    {
        roleRepository.save(role);

    }

    @Override
    public HashSet<Role> getAllRoles()
    {

        return new HashSet<>(roleRepository.findAll());
    }

    @Override
    public Role findRoleByName(String name)
    {
        return roleRepository.findByName(name);
    }

    @Override
    public void deleteRole(Role role)
    {
        roleRepository.delete(role);
    }

}

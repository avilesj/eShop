package com.hercule.eshop.services;

import com.hercule.eshop.models.Role;
import com.hercule.eshop.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;

public class RoleServiceImpl implements RoleService
{

    @Autowired
    RoleRepository roleRepository;

    @Override
    public void saveRole(Role role)
    {
        roleRepository.save(role);

    }

    @Override
    public HashSet<Role> getAllRoles()
    {

        return new HashSet<Role>(roleRepository.findAll());
    }

}

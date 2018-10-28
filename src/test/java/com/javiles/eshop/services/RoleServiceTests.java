package com.javiles.eshop.services;

import com.javiles.eshop.models.Role;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.HashSet;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@ActiveProfiles("dev")
public class RoleServiceTests
{

    @Autowired
    private RoleService roleService;

    private Role role;

    @Before
    public void initialize()
    {
        this.role = new Role();
        this.role.setName("ROLE_TEST");
    }

    @Test
    public void createsRoleAndFindsItName()
    {
        roleService.saveRole(this.role);
        Role dbRole = roleService.findRoleByName(this.role.getName());
        assertEquals(this.role.getName(), dbRole.getName());
    }

    @Test
    public void retrievesAllRoles()
    {
        Role dbRole = new Role();
        dbRole.setName("ROLE_TEST2");

        roleService.saveRole(this.role);
        roleService.saveRole(dbRole);

        HashSet<Role> roles = roleService.getAllRoles();

        assertNotNull(roles);
        assertTrue("More than 1 role was retrieved", roles.size() > 1);
    }

    @Test
    public void deletesRoleFromDatabase()
    {
        roleService.saveRole(this.role);

        Role dbRole = roleService.findRoleByName(this.role.getName());

        roleService.deleteRole(dbRole);

        dbRole = roleService.findRoleByName(this.role.getName());

        assertNull(dbRole);
    }


}

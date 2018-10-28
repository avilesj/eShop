package com.javiles.eshop.services;

import com.javiles.eshop.models.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@ActiveProfiles("dev")
public class UserDetailsServiceTests
{

    @Autowired
    private UserService userService;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private RoleService roleService;

    private User user;

    @Before
    public void initialize()
    {
        user = new User();
        user.setUsername("javiles");
        user.setPassword("321321");
        user.setRoles(roleService.getAllRoles());
        userService.save(user);
    }

    @Test
    public void loadsUser()
    {
        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());
        assertEquals(user.getUsername(), userDetails.getUsername());
    }


}

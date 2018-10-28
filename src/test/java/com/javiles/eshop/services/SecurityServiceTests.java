package com.javiles.eshop.services;

import com.javiles.eshop.models.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@ActiveProfiles("dev")
public class SecurityServiceTests
{

    @Autowired
    private UserService userService;
    @Autowired
    private SecurityService securityService;

    @Autowired
    private RoleService roleService;

    private User user;
    private User loginUser;


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
    public void userLogsIn()
    {
        loginUser = new User();
        loginUser.setUsername("javiles");
        loginUser.setPassword("321321");
        securityService.autologin(loginUser.getUsername(), loginUser.getPassword());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        assertEquals(loginUser.getUsername(), authentication.getName());
    }

    @Test
    public void findLoggedInUsername()
    {
        loginUser = new User();
        loginUser.setUsername("javiles");
        loginUser.setPassword("321321");
        securityService.autologin(loginUser.getUsername(), loginUser.getPassword());

        String loggedUsername = securityService.findLoggedInUsername();

        assertEquals(loginUser.getUsername(), loggedUsername);
    }


}

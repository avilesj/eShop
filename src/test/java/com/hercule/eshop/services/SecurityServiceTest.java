package com.hercule.eshop.services;

import com.hercule.eshop.models.User;
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
public class SecurityServiceTest
{

    @Autowired
    UserService userService;
    @Autowired
    SecurityService securityService;

    User user;
    User loginUser;

    @Before
    public void initialize()
    {
        user = new User();
        user.setUsername("javiles");
        user.setPassword("321321");
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

package com.hercule.eshop.services;

import com.hercule.eshop.models.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@ActiveProfiles("dev")
public class UserServiceTests
{

    @Autowired
    private UserService userService;

    private User user;

    @Before
    public void initialize()
    {
        user = new User();
        user.setUsername("javiles");
        user.setPassword("321321");
    }

    @Test
    public void createsUserAndFindsItByUsername()
    {
        userService.save(user);
        User dbUser = userService.findByUsername(this.user.getUsername());
        assertEquals(this.user.getUsername(), dbUser.getUsername());
    }


}

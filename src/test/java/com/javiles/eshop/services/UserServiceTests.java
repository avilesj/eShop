package com.javiles.eshop.services;

import com.javiles.eshop.models.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.List;

import static org.junit.Assert.*;

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

    @Test
    public void findsForUsersByUsername()
    {
        User dbUser = new User();
        dbUser.setUsername("j_aviles");
        dbUser.setPassword("321321");

        userService.save(user);
        userService.save(dbUser);

        List<User> userSearchResult = userService.searchUserByUsername("aviles");

        assertNotNull(userSearchResult);
        assertEquals(2, userSearchResult.size());
    }

    @Test
    public void findsOneById()
    {
        userService.save(user);

        User dbUser = userService.findByUserId(user.getId());

        assertNotNull(dbUser);
        assertEquals(user.getUsername(), dbUser.getUsername());
        assertEquals(user.getId(), dbUser.getId());
    }

    @Test
    public void deletesUser()
    {
        userService.save(user);
        User dbUser = userService.findByUsername(user.getUsername());
        userService.deleteUserById(dbUser.getId());

        dbUser = userService.findByUsername(user.getUsername());

        assertNull(dbUser);
    }


}

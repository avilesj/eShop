package com.hercule.eshop.services;

import com.hercule.eshop.models.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@ActiveProfiles("dev")
public class CartServiceTests
{
    @Autowired
    private CartService cartService;

    @Autowired
    private UserService userService;

    private User user;


    @Before
    public void initialize()
    {
        this.user = new User();
        this.user.setUsername("javiles");
        this.user.setPassword("321321");

    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void validateCartCreationOnUserCreation()
    {
        userService.save(this.user);
        User dbUser = userService.findByUsername(this.user.getUsername());

        assertNotNull(dbUser.getCart());
    }
}

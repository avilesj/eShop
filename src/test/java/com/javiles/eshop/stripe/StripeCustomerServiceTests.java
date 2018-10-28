package com.javiles.eshop.stripe;

import com.javiles.eshop.models.User;
import com.javiles.eshop.services.UserService;
import com.javiles.eshop.stripe.models.StripeCustomer;
import com.javiles.eshop.stripe.repositories.StripeRepository;
import com.javiles.eshop.stripe.services.StripeCustomerService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("dev")
public class StripeCustomerServiceTests
{
    @Autowired
    private StripeRepository stripeRepository;

    @Autowired
    private StripeCustomerService stripeCustomerService;

    @Autowired
    private UserService userService;

    private User user;

    @Before
    public void init()
    {
        user = new User();
        user.setUsername("bobvance");
        user.setPassword("password");
        userService.save(user);
    }

    @After
    public void terminate()
    {
        userService.deleteUserById(user.getId());
    }

    @Test
    public void shouldSaveCustomerOnDatabase()
    {
        User dbUser = userService.findByUsername(user.getUsername());
        stripeCustomerService.saveStripeCustomer(dbUser, "token");
        StripeCustomer stripeCustomer = stripeCustomerService.getStripeCustomerByUserId(dbUser.getId());
        assertNotNull(stripeCustomer);
        assertEquals(stripeCustomer.getUser().getUsername(), dbUser.getUsername());

    }

}

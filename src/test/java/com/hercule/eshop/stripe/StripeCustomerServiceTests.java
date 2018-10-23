package com.hercule.eshop.stripe;

import com.hercule.eshop.models.User;
import com.hercule.eshop.services.UserService;
import com.hercule.eshop.stripe.models.StripeCustomer;
import com.hercule.eshop.stripe.repositories.StripeRepository;
import com.hercule.eshop.stripe.services.StripeCustomerService;
import com.stripe.exception.StripeException;
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


    @Test
    public void shouldSaveCustomerOnDatabase() throws StripeException
    {
        User user = new User();
        user.setUsername("bobvance");
        user.setPassword("password");
        userService.save(user);

        User dbUser = userService.findByUsername(user.getUsername());
        stripeCustomerService.saveStripeCustomer(dbUser, "token");

        StripeCustomer stripeCustomer = stripeCustomerService.getStripeCustomerByUserId(dbUser.getId());
        assertNotNull(stripeCustomer);
        assertEquals(stripeCustomer.getUser().getUsername(), dbUser.getUsername());

    }
}

package com.hercule.eshop.stripe;

import com.hercule.eshop.models.User;
import com.hercule.eshop.services.UserService;
import com.hercule.eshop.stripe.models.StripeCustomer;
import com.hercule.eshop.stripe.models.StripeCustomerCard;
import com.hercule.eshop.stripe.services.StripeCustomerCardService;
import com.hercule.eshop.stripe.services.StripeCustomerService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@ActiveProfiles("dev")
public class StripeCustomerCardTests
{
    @Autowired
    StripeCustomerCardService stripeCustomerCardService;

    @Autowired
    UserService userService;
    @Autowired
    StripeCustomerService stripeCustomerService;

    private User user;
    private StripeCustomer stripeCustomer;

    @Before
    public void init()
    {
        user = new User();
        user.setUsername("michaelscott");
        user.setPassword("321321");
        userService.save(user);

        stripeCustomerService.saveStripeCustomer(user, "token");
        stripeCustomer = stripeCustomerService.getStripeCustomerByUserId(user.getId());
    }

    @Test
    public void shouldStoreCardOnDatabase()
    {
        stripeCustomerCardService.saveCustomerCard(stripeCustomer, "token", "9999");

        StripeCustomerCard dbStripeCustomerCard = stripeCustomerCardService.getCardByToken("token");
        assertNotNull(dbStripeCustomerCard);
        assertEquals(dbStripeCustomerCard.getLastFour(), "9999");
    }
}

package com.hercule.eshop.stripe;

import com.hercule.eshop.models.User;
import com.hercule.eshop.services.UserService;
import com.hercule.eshop.stripe.models.StripeCustomer;
import com.hercule.eshop.stripe.models.StripeCustomerCard;
import com.hercule.eshop.stripe.repositories.StripeCustomerCardRepository;
import com.hercule.eshop.stripe.repositories.StripeRepository;
import com.hercule.eshop.stripe.services.StripeCustomerService;
import com.stripe.exception.StripeException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Set;

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

    @Autowired
    private StripeCustomerCardRepository stripeCustomerCardRepository;

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
    public void shouldSaveCustomerOnDatabase() throws StripeException
    {
        User dbUser = userService.findByUsername(user.getUsername());
        stripeCustomerService.saveStripeCustomer(dbUser, "token");
        StripeCustomer stripeCustomer = stripeCustomerService.getStripeCustomerByUserId(dbUser.getId());
        assertNotNull(stripeCustomer);
        assertEquals(stripeCustomer.getUser().getUsername(), dbUser.getUsername());

    }

    @Test
    public void shouldSaveMultiplePaymentMethods()
    {
        User dbUser = userService.findByUsername(user.getUsername());
        stripeCustomerService.saveStripeCustomer(dbUser, "token");
        StripeCustomer stripeCustomer = stripeCustomerService.getStripeCustomerByUserId(dbUser.getId());

        stripeCustomerService.addCardToCustomer(stripeCustomer, "token1", "4321");
        stripeCustomerService.addCardToCustomer(stripeCustomer, "token2", "3541");
        stripeCustomerService.addCardToCustomer(stripeCustomer, "token3", "9874");
        stripeCustomerService.addCardToCustomer(stripeCustomer, "token4", "1114");

        StripeCustomer dbStripeCustomer = stripeCustomerService.getStripeCustomerByUserId(dbUser.getId());

//        HashSet<StripeCustomerCard> stripeCustomerCards = new HashSet<StripeCustomerCard>(stripeCustomerCardRepository.findAll());
        Set<StripeCustomerCard> stripeCustomerCards = dbStripeCustomer.getStripeCustomerCards();

        assertNotNull(stripeCustomerCards);
        assertEquals(4, stripeCustomerCards.size());
    }
}

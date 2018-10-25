package com.hercule.eshop.stripe;

import com.hercule.eshop.models.User;
import com.hercule.eshop.services.UserService;
import com.hercule.eshop.stripe.models.StripeCustomer;
import com.hercule.eshop.stripe.services.StripeCustomerService;
import com.hercule.eshop.stripe.services.StripeService;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Token;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

import static junit.framework.TestCase.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("dev")
public class StripeRepositoryTests
{
    @Autowired
    private StripeService stripeService;

    @Autowired
    private StripeProperties stripeProperties;

    @Autowired
    private StripeCustomerService stripeCustomerService;

    @Autowired
    private UserService userService;


    @Before
    public void init()
    {
        Stripe.apiKey = stripeProperties.getSecretKey();
    }

    @Test
    public void shouldMakePaymentToStripe() throws StripeException
    {
        User user = new User();
        user.setUsername("bobvance");
        user.setPassword("password");

        Map<String, Object> cardParam = new HashMap<>();
        cardParam.put("number", "4242424242424242");
        cardParam.put("exp_month", "11");
        cardParam.put("exp_year", "2022");
        cardParam.put("cvc", "123");

        Map<String, Object> tokenParam = new HashMap<>();
        tokenParam.put("card", cardParam);
        Token token = Token.create(tokenParam);

        userService.save(user);
        stripeService.addNewCustomer(token.getId(), user);

        stripeService.makePayment(user, 1.50);
    }

    @Test
    public void shouldAddNewCustomerAndSaveToDatabase() throws StripeException
    {
        User user = new User();
        user.setUsername("bobvance");
        user.setPassword("password");

        Map<String, Object> cardParam = new HashMap<>();
        cardParam.put("number", "4242424242424242");
        cardParam.put("exp_month", "11");
        cardParam.put("exp_year", "2022");
        cardParam.put("cvc", "123");

        Map<String, Object> tokenParam = new HashMap<>();
        tokenParam.put("card", cardParam);
        Token token = Token.create(tokenParam);

        userService.save(user);
        stripeService.addNewCustomer(token.getId(), user);

        StripeCustomer stripeCustomer = stripeCustomerService.getStripeCustomerByUserId(user.getId());
        assertNotNull(stripeCustomer);
    }
}

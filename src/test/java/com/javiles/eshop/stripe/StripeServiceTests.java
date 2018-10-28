package com.javiles.eshop.stripe;

import com.javiles.eshop.models.User;
import com.javiles.eshop.services.UserService;
import com.javiles.eshop.stripe.models.StripeCustomer;
import com.javiles.eshop.stripe.models.StripeCustomerPayment;
import com.javiles.eshop.stripe.services.StripeService;
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

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@ActiveProfiles("dev")
public class StripeServiceTests
{
    @Autowired
    private StripeService stripeService;

    @Autowired
    private StripeProperties stripeProperties;

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
        stripeService.makePayment(user, 2.50);
        stripeService.makePayment(user, 5.50);
        stripeService.makePayment(user, 10.00);

        StripeCustomer stripeCustomer = stripeService.getCustomerByUserId(user.getId());
        List<StripeCustomerPayment> stripeCustomerPayments = stripeService.getCustomerPaymentsByUserId(user.getId());

        assertNotNull(stripeCustomerPayments);
        assertEquals(4, stripeCustomerPayments.size());
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

        StripeCustomer stripeCustomer = stripeService.getCustomerByUserId(user.getId());

        assertNotNull(stripeCustomer);
    }
}

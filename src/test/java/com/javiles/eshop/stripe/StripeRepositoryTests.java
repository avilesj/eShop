package com.javiles.eshop.stripe;

import com.javiles.eshop.stripe.repositories.StripeRepository;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
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
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@ActiveProfiles("dev")
public class StripeRepositoryTests
{
    @Autowired
    private StripeRepository stripeRepository;

    @Autowired
    private StripeProperties stripeProperties;

    @Before
    public void init()
    {
        Stripe.apiKey = this.stripeProperties.getSecretKey();
    }

    @Test
    public void shouldCreateNewStripeCustomer() throws StripeException
    {
        Map<String, Object> cardParam = new HashMap<>();
        cardParam.put("number", "4242424242424242");
        cardParam.put("exp_month", "11");
        cardParam.put("exp_year", "2022");
        cardParam.put("cvc", "123");

        Map<String, Object> tokenParam = new HashMap<>();
        tokenParam.put("card", cardParam);
        Token token = Token.create(tokenParam);

        String customerToken = stripeRepository.createNewCustomer(token.getId());

        assertNotNull(customerToken);
    }

    @Test
    public void shouldMakePaymentToStripeCustomer() throws StripeException
    {
        Map<String, Object> cardParam = new HashMap<>();
        cardParam.put("number", "4242424242424242");
        cardParam.put("exp_month", "11");
        cardParam.put("exp_year", "2022");
        cardParam.put("cvc", "123");

        Map<String, Object> tokenParam = new HashMap<>();
        tokenParam.put("card", cardParam);
        Token token = Token.create(tokenParam);

        String customerToken = stripeRepository.createNewCustomer(token.getId());
        Charge charge = stripeRepository.makePayment(customerToken, 100);

        assertNotNull(charge);
        assertEquals(100, charge.getAmount().longValue());
    }
}

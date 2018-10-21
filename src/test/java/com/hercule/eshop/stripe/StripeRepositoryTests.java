package com.hercule.eshop.stripe;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Token;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("dev")
public class StripeRepositoryTests
{
    @Autowired
    private StripeService stripeService;

    @Autowired
    private StripeProperties stripeProperties;

    @Test
    public void shouldMakePaymentToStripe() throws StripeException
    {
        Stripe.apiKey = stripeProperties.getSecretKey();

        Map<String, Object> cardParam = new HashMap<String, Object>();
        cardParam.put("number", "4242424242424242");
        cardParam.put("exp_month", "11");
        cardParam.put("exp_year", "2022");
        cardParam.put("cvc", "123");

        Map<String, Object> tokenParam = new HashMap<String, Object>();
        tokenParam.put("card", cardParam);

        Token token = Token.create(tokenParam);

        stripeService.makePayment(token.getId());
    }
}

package com.hercule.eshop.stripe;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

public class StripeRepository
{

    @Autowired
    StripeProperties stripeProperties;

    private String keyFromPropertiesFile = "sk_test_14yHBwRtc3vSrMbiUgXhzfTK";

    public void makePayment(String token) throws StripeException
    {

        Stripe.apiKey = stripeProperties.getSecretKey();
        Map<String, Object> chargeMap = new HashMap<String, Object>();
        chargeMap.put("amount", 100);
        chargeMap.put("currency", "usd");
        chargeMap.put("source", token); // obtained via Stripe.js

        try
        {
            Charge charge = Charge.create(chargeMap);
            System.out.println(charge);
        } catch (StripeException e)
        {
            e.printStackTrace();
        }
    }
}
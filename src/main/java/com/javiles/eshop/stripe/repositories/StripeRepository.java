package com.javiles.eshop.stripe.repositories;

import com.javiles.eshop.stripe.StripeProperties;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

public class StripeRepository
{
    @Autowired
    private StripeProperties stripeProperties;

    public Charge makePayment(String customerId, int amount)
    {
        Charge charge = null;
        Stripe.apiKey = stripeProperties.getSecretKey();

        Map<String, Object> chargeMap = new HashMap<>();
        chargeMap.put("amount", amount);
        chargeMap.put("currency", "usd");
        chargeMap.put("customer", customerId);

        try
        {
            charge = Charge.create(chargeMap);
            System.out.println(charge);
            return charge;
        } catch (StripeException e)
        {
            e.printStackTrace();
        }

        return charge;

    }

    public String createNewCustomer(String token) throws StripeException
    {
        Stripe.apiKey = stripeProperties.getSecretKey();

        Map<String, Object> chargeParams = new HashMap<>();
        chargeParams.put("source", token);
        chargeParams.put("email", "paying.user@example.com");
        Customer customer = Customer.create(chargeParams);

        return customer.getId();
    }
}
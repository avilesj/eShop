package com.hercule.eshop.stripe;

import com.stripe.exception.StripeException;
import org.springframework.beans.factory.annotation.Autowired;

public class StripeServiceImpl implements StripeService
{

    @Autowired
    StripeRepository stripeRepository;

    @Override
    public void makePayment(String token) throws StripeException
    {
        stripeRepository.makePayment(token);
    }
}

package com.hercule.eshop.stripe;

import com.stripe.exception.StripeException;

public interface StripeService
{
    public void makePayment(String token) throws StripeException;
}

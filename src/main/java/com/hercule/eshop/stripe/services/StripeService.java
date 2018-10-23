package com.hercule.eshop.stripe.services;

import com.hercule.eshop.models.User;
import com.stripe.exception.StripeException;

public interface StripeService
{
    public void makePayment(String token) throws StripeException;

    public void addNewCustomer(String token, User user) throws StripeException;
}

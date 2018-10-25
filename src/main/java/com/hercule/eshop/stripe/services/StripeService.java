package com.hercule.eshop.stripe.services;

import com.hercule.eshop.models.User;
import com.hercule.eshop.stripe.models.StripeCustomer;
import com.stripe.exception.StripeException;

public interface StripeService
{
    void makePayment(User user, double amount) throws StripeException;

    void addNewCustomer(String token, User user) throws StripeException;

    void deleteCustomer(User user);

    StripeCustomer getCustomerByUserId(long UserId);
}

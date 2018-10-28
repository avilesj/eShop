package com.javiles.eshop.stripe.services;

import com.javiles.eshop.models.User;
import com.javiles.eshop.stripe.models.StripeCustomer;

public interface StripeCustomerService
{
    void saveStripeCustomer(User user, String key);

    StripeCustomer getStripeCustomerById(long id);

    StripeCustomer getStripeCustomerByUserId(long id);

    StripeCustomer getStripeCustomerByToken(String token);

    void deleteStripeCustomer(StripeCustomer stripeCustomer);
}

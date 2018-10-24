package com.hercule.eshop.stripe.services;

import com.hercule.eshop.models.User;
import com.hercule.eshop.stripe.models.StripeCustomer;

public interface StripeCustomerService
{
    void saveStripeCustomer(User user, String key);

    StripeCustomer getStripeCustomerById(long id);

    StripeCustomer getStripeCustomerByUserId(long id);

    StripeCustomer getStripeCustomerByToken(String token);

    void deleteStripeCustomer(StripeCustomer stripeCustomer);
}

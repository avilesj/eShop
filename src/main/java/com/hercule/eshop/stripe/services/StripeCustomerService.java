package com.hercule.eshop.stripe.services;

import com.hercule.eshop.models.User;
import com.hercule.eshop.stripe.models.StripeCustomer;

public interface StripeCustomerService
{
    public void saveStripeCustomer(User user, String key);

    public StripeCustomer getStripeCustomerById(long id);

    public StripeCustomer getStripeCustomerByUserId(long id);

    public StripeCustomer getStripeCustomerByToken(String token);
}

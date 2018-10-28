package com.javiles.eshop.stripe.services;

import com.javiles.eshop.models.User;
import com.javiles.eshop.stripe.models.StripeCustomer;
import com.javiles.eshop.stripe.repositories.StripeCustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class StripeCustomerServiceImpl implements StripeCustomerService
{
    @Autowired
    private StripeCustomerRepository stripeCustomerRepository;

    @Override
    public void saveStripeCustomer(User user, String key)
    {
        StripeCustomer stripeCustomer = new StripeCustomer();
        stripeCustomer.setUser(user);
        stripeCustomer.setCustomerId(key);

        stripeCustomerRepository.save(stripeCustomer);
    }

    @Override
    public StripeCustomer getStripeCustomerById(long id)
    {
        return stripeCustomerRepository.findById(id);
    }

    @Override
    public StripeCustomer getStripeCustomerByUserId(long id)
    {
        return stripeCustomerRepository.findByUserId(id);
    }

    @Override
    public StripeCustomer getStripeCustomerByToken(String token)
    {
        return stripeCustomerRepository.findBycustomerId(token);
    }

    @Override
    public void deleteStripeCustomer(StripeCustomer stripeCustomer)
    {
        stripeCustomerRepository.delete(stripeCustomer);
    }
}

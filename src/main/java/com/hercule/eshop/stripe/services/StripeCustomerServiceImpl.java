package com.hercule.eshop.stripe.services;

import com.hercule.eshop.models.User;
import com.hercule.eshop.stripe.models.StripeCustomer;
import com.hercule.eshop.stripe.repositories.StripeCustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class StripeCustomerServiceImpl implements StripeCustomerService
{
    @Autowired
    StripeCustomerRepository stripeCustomerRepository;

    @Autowired
    StripeCustomerCardService stripeCustomerCardService;

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
    public void addCardToCustomer(StripeCustomer stripeCustomer, String token, String lastFour)
    {
        stripeCustomerCardService.saveCustomerCard(stripeCustomer, token, lastFour);
    }

    @Override
    public void deleteStripeCustomer(StripeCustomer stripeCustomer)
    {
        stripeCustomerCardService.deleteAllCustomerCards(stripeCustomer);
        stripeCustomerRepository.delete(stripeCustomer);
    }
}

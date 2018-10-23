package com.hercule.eshop.stripe.services;

import com.hercule.eshop.stripe.models.StripeCustomerCard;
import com.hercule.eshop.stripe.repositories.StripeCustomerCardRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

public class StripeCustomerCardServiceImpl implements StripeCustomerCardService
{
    @Autowired
    StripeCustomerCardRepository stripeCustomerCardRepository;

    @Override
    public StripeCustomerCard getCardByToken(String token)
    {
        return stripeCustomerCardRepository.findByToken(token);
    }

    @Override
    public StripeCustomerCard getCardById(long id)
    {
        return stripeCustomerCardRepository.findById(id);
    }

    @Override
    public Set<StripeCustomerCard> getAllCardsFromCustomer(long customerId)
    {
        return stripeCustomerCardRepository.findByStripeCustomerId(customerId);
    }
}

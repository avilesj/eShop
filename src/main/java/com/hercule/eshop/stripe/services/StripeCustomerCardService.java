package com.hercule.eshop.stripe.services;

import com.hercule.eshop.stripe.models.StripeCustomer;
import com.hercule.eshop.stripe.models.StripeCustomerCard;

import java.util.Set;

public interface StripeCustomerCardService
{
    StripeCustomerCard getCardByToken(String token);

    StripeCustomerCard getCardById(long id);

    Set<StripeCustomerCard> getAllCardsFromCustomer(long customerId);

    void saveCustomerCard(StripeCustomer stripeCustomer, String token, String lastFour);

    void deleteCustomerCardByToken(String token);

    void deleteAllCustomerCards(StripeCustomer stripeCustomer);
}

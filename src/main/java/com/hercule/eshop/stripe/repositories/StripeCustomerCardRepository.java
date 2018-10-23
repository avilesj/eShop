package com.hercule.eshop.stripe.repositories;

import com.hercule.eshop.stripe.models.StripeCustomerCard;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface StripeCustomerCardRepository extends CrudRepository<StripeCustomerCard, Long>
{
    StripeCustomerCard findById(long id);

    Set<StripeCustomerCard> findByStripeCustomerId(long id);

    StripeCustomerCard findByToken(String token);
}

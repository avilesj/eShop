package com.hercule.eshop.stripe.repositories;

import com.hercule.eshop.stripe.models.StripeCustomerCard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface StripeCustomerCardRepository extends JpaRepository<StripeCustomerCard, Long>
{
    StripeCustomerCard findById(long id);

    Set<StripeCustomerCard> findByStripeCustomerId(long id);

    StripeCustomerCard findByToken(String token);

    void deleteByToken(String token);

    void deleteByStripeCustomerId(long StripeCustomerId);
}

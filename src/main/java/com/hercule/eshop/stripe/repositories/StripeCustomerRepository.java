package com.hercule.eshop.stripe.repositories;

import com.hercule.eshop.stripe.models.StripeCustomer;
import org.springframework.data.repository.CrudRepository;

public interface StripeCustomerRepository extends CrudRepository<StripeCustomer, Long>
{
    StripeCustomer findById(long id);

    StripeCustomer findBycustomerId(String customerId);

    StripeCustomer findByUserId(long id);
}

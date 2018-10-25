package com.hercule.eshop.stripe.repositories;

import com.hercule.eshop.stripe.models.StripeCustomerPayment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StripeCustomerPaymentRepository extends JpaRepository<StripeCustomerPayment, Long>
{
    List<StripeCustomerPayment> findByStripeCustomerId(long stripeCustomerId);
}

package com.javiles.eshop.stripe.repositories;

import com.javiles.eshop.stripe.models.StripeCustomerPayment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StripeCustomerPaymentRepository extends JpaRepository<StripeCustomerPayment, Long>
{
    List<StripeCustomerPayment> findByStripeCustomerId(long stripeCustomerId);
}

package com.javiles.eshop.stripe.services;

import com.javiles.eshop.stripe.models.StripeCustomer;
import com.javiles.eshop.stripe.models.StripeCustomerPayment;
import com.stripe.model.Charge;

import java.util.List;

public interface StripeCustomerPaymentService
{
    void saveCustomerPayment(StripeCustomer stripeCustomer, Charge charge);

    List<StripeCustomerPayment> getCustomerPaymentByCustomerId(long customerId);
}

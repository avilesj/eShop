package com.javiles.eshop.stripe.services;

import com.javiles.eshop.stripe.models.StripeCustomer;
import com.javiles.eshop.stripe.models.StripeCustomerPayment;
import com.javiles.eshop.stripe.repositories.StripeCustomerPaymentRepository;
import com.stripe.model.Charge;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class StripeCustomerPaymentServiceImpl implements StripeCustomerPaymentService
{
    @Autowired
    private StripeCustomerPaymentRepository stripeCustomerPaymentRepository;

    @Override
    public void saveCustomerPayment(StripeCustomer stripeCustomer, Charge charge)
    {
        StripeCustomerPayment stripeCustomerPayment = new StripeCustomerPayment();
        stripeCustomerPayment.setAmountInCents(charge.getAmount());
        stripeCustomerPayment.setCurrency(charge.getCurrency());
        stripeCustomerPayment.setPaymentId(charge.getId());
        stripeCustomerPayment.setStripeCustomer(stripeCustomer);

        stripeCustomerPaymentRepository.save(stripeCustomerPayment);
    }

    @Override
    public List<StripeCustomerPayment> getCustomerPaymentByCustomerId(long customerId)
    {
        return stripeCustomerPaymentRepository.findByStripeCustomerId(customerId);
    }
}

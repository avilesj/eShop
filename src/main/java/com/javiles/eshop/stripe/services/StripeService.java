package com.javiles.eshop.stripe.services;

import com.javiles.eshop.models.User;
import com.javiles.eshop.stripe.models.StripeCustomer;
import com.javiles.eshop.stripe.models.StripeCustomerPayment;
import com.stripe.exception.StripeException;

import java.util.List;

public interface StripeService
{
    void makePayment(User user, double amount) throws StripeException;

    void addNewCustomer(String token, User user) throws StripeException;

    void deleteCustomer(User user);

    StripeCustomer getCustomerByUserId(long userId);

    List<StripeCustomerPayment> getCustomerPaymentsByUserId(long userId);
}

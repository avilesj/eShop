package com.hercule.eshop.stripe.services;

import com.hercule.eshop.models.User;
import com.hercule.eshop.services.UserService;
import com.hercule.eshop.stripe.models.StripeCustomer;
import com.hercule.eshop.stripe.models.StripeCustomerPayment;
import com.hercule.eshop.stripe.repositories.StripeRepository;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class StripeServiceImpl implements StripeService
{

    @Autowired
    StripeRepository stripeRepository;

    @Autowired
    StripeCustomerService stripeCustomerService;

    @Autowired
    StripeCustomerPaymentService stripeCustomerPaymentService;

    @Autowired
    UserService userService;

    @Override
    public void makePayment(User user, double amount)
    {
        StripeCustomer stripeCustomer = stripeCustomerService.getStripeCustomerByUserId(user.getId());

        int cents = (int) Math.round((amount) * 100);
        String customerId = stripeCustomer.getCustomerId();
        Charge charge = stripeRepository.makePayment(customerId, cents);

        stripeCustomerPaymentService.saveCustomerPayment(stripeCustomer, charge);
    }

    @Override
    public void addNewCustomer(String token, User user) throws StripeException
    {
        String customerId = stripeRepository.createNewCustomer(token);
        stripeCustomerService.saveStripeCustomer(user, customerId);
    }

    @Override
    public void deleteCustomer(User user)
    {
        StripeCustomer stripeCustomer = stripeCustomerService.getStripeCustomerByUserId(user.getId());
        stripeCustomerService.deleteStripeCustomer(stripeCustomer);
    }

    @Override
    public StripeCustomer getCustomerByUserId(long UserId)
    {
        return stripeCustomerService.getStripeCustomerByUserId(UserId);
    }

    @Override
    public List<StripeCustomerPayment> getCustomerPaymentsByUserId(long userId)
    {
        StripeCustomer stripeCustomer = stripeCustomerService.getStripeCustomerByUserId(userId);
        return stripeCustomerPaymentService.getCustomerPaymentByCustomerId(stripeCustomer.getId());
    }

}

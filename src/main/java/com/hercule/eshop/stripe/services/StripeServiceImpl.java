package com.hercule.eshop.stripe.services;

import com.hercule.eshop.models.User;
import com.hercule.eshop.services.UserService;
import com.hercule.eshop.stripe.models.StripeCustomer;
import com.hercule.eshop.stripe.repositories.StripeRepository;
import com.stripe.exception.StripeException;
import org.springframework.beans.factory.annotation.Autowired;

public class StripeServiceImpl implements StripeService
{

    @Autowired
    StripeRepository stripeRepository;

    @Autowired
    StripeCustomerService stripeCustomerService;

    @Autowired
    UserService userService;

    @Override
    public void makePayment(User user, double amount)
    {
        StripeCustomer stripeCustomer = stripeCustomerService.getStripeCustomerByUserId(user.getId());

        int cents = (int) Math.round((amount) * 100);
        String customerId = stripeCustomer.getCustomerId();
        stripeRepository.makePayment(customerId, cents);
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

}

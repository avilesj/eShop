package com.javiles.eshop.controllers;

import com.javiles.eshop.models.User;
import com.javiles.eshop.services.UserService;
import com.javiles.eshop.stripe.services.StripeService;
import com.stripe.exception.StripeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
public class StripeController
{
    @Autowired
    private StripeService stripeService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/addStripe", method = RequestMethod.POST)
    public String makePayment(@RequestParam("token") String token, Principal principal) throws StripeException
    {
        User user = userService.findByUsername(principal.getName());
        stripeService.addNewCustomer(token, user);
        return "redirect:/admin/";
    }
}

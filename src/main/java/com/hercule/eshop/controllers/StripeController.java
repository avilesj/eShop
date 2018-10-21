package com.hercule.eshop.controllers;

import com.hercule.eshop.stripe.StripeService;
import com.stripe.exception.StripeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class StripeController
{
    @Autowired
    StripeService stripeService;

    @RequestMapping(value = "/payment", method = RequestMethod.POST)
    public String makePayment(@RequestParam("token") String token) throws StripeException
    {
        stripeService.makePayment(token);
        return "redirect:/admin/";
    }
}

package com.javiles.eshop.controllers.user;

import com.javiles.eshop.models.User;
import com.javiles.eshop.services.UserService;
import com.javiles.eshop.stripe.models.StripeCustomer;
import com.javiles.eshop.stripe.services.StripeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@RequestMapping("/settings")
@Controller
public class UserSettingsController
{
    @Autowired
    private UserService userService;

    @Autowired
    private StripeService stripeService;

    @RequestMapping("")
    public String getUserSettingsIndex()
    {
        return "users/userSettings";
    }

    @RequestMapping("/payment")
    public String getUserPaymentMehtods(Principal principal, Model model)
    {
        User user = userService.findByUsername(principal.getName());
        StripeCustomer stripeCustomer = stripeService.getCustomerByUserId(user.getId());

        model.addAttribute("stripeCustomer", stripeCustomer);

        return "users/paymentMethods";
    }

    @RequestMapping("/stripe")
    public String getStripeForm(Principal principal, Model model)
    {
        User user = userService.findByUsername(principal.getName());
        StripeCustomer stripeCustomer = stripeService.getCustomerByUserId(user.getId());
        if (stripeCustomer != null)
        {
            return "redirect:/settings/payment";
        }
        return "users/stripe";
    }
}

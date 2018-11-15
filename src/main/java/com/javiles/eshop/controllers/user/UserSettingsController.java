package com.javiles.eshop.controllers.user;

import com.javiles.eshop.models.Country;
import com.javiles.eshop.models.User;
import com.javiles.eshop.services.CountryService;
import com.javiles.eshop.services.UserService;
import com.javiles.eshop.spring.UserValidator;
import com.javiles.eshop.stripe.models.StripeCustomer;
import com.javiles.eshop.stripe.services.StripeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;

@RequestMapping("/settings")
@Controller
public class UserSettingsController
{
    @Autowired
    private UserService userService;

    @Autowired
    private StripeService stripeService;

    @Autowired
    private CountryService countryService;

    @Autowired
    private UserValidator userValidator;

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

    @RequestMapping("/personalinfo")
    public String getUserPersonalInfo(Principal principal, Model model)
    {
        User user = userService.findByUsername(principal.getName());
        model.addAttribute("userForm", user);
        model.addAttribute("countries", countryService.getAllCountries());
        return "users/personalInfo";
    }

    @RequestMapping(value = "/personalinfo", method = RequestMethod.POST)
    public String updateUserPersonalInfo(@ModelAttribute("userForm") User userForm, Principal principal, Model model)
    {
        Country country = countryService.getCountryByCode(userForm.getCountry().getCountryCode());
        userForm.setUsername(principal.getName());
        userForm.setCountry(country);
        userService.updateUserPersonalInformation(userForm);
        return "redirect:/settings";
    }

    @RequestMapping(value = "/changepassword", method = RequestMethod.POST)
    public String method(Principal principal, Model model, @ModelAttribute("currentPassword") String currentPassword,
                         @ModelAttribute("userForm") User userForm, BindingResult bindingResult)
    {
        userForm.setUsername(principal.getName());
        userValidator.validateNewPassword(currentPassword, userForm, bindingResult);

        if (bindingResult.hasErrors())
        {
            return "users/changePassword";
        }

        userService.updateUserPassword(userForm);

        return "redirect:/settings";
    }

    @RequestMapping("/changepassword")
    public String getUserNewPasswordForm(Model model)
    {
        model.addAttribute("currentPassword", "currentPassword");
        model.addAttribute("userForm", new User());
        return "users/changePassword";
    }


}

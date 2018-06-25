package com.hercule.eshop.controllers;

import org.h2.util.New;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.hercule.eshop.UserValidator;
import com.hercule.eshop.models.User;
import com.hercule.eshop.services.SecurityService;
import com.hercule.eshop.services.UserService;

@Controller
public class UserController {

	@Autowired
	UserService userService;
	
	@Autowired
	SecurityService securityService;
	
	@Autowired
	UserValidator userValidator;
	
	@RequestMapping(value = "/registration", method = RequestMethod.GET)
	public String registration(Model model)
	{
		model.addAttribute("userForm", new User());
		
		return "users/registration";
	}
	
	@RequestMapping(value = "/registration", method = RequestMethod.POST)
	public String registration(final User userForm, BindingResult bindingResult)
	{
		userValidator.validate(userForm, bindingResult);
		
		if(bindingResult.hasErrors())
		{
			return "users/registration";
		}
		
		userService.save(userForm);
		
		securityService.autologin(userForm.getUsername(), userForm.getPassword());
		
		return "redirect:/";
	}
	
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model, String error, String logout)
    {
        if (error != null)
            model.addAttribute("error", "Your username and password is invalid.");

        if (logout != null)
            model.addAttribute("message", "You have been logged out successfully.");

        return "index";
    }
}

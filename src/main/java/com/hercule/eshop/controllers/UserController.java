package com.hercule.eshop.controllers;

import javax.servlet.http.HttpServletRequest;

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
		
		return "redirect:/";
	}
	
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(HttpServletRequest request)
    {
    	String username = request.getParameter("username");
    	String password = request.getParameter("password");
    	
    	securityService.autologin(username, password);
    	
        return "redirect:/";
    }
}

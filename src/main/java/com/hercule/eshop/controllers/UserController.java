package com.hercule.eshop.controllers;

import com.hercule.eshop.models.Role;
import com.hercule.eshop.models.User;
import com.hercule.eshop.services.RoleService;
import com.hercule.eshop.services.SecurityService;
import com.hercule.eshop.services.UserService;
import com.hercule.eshop.spring.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.HashSet;

@Controller
public class UserController
{

    @Autowired
    UserService userService;

    @Autowired
    SecurityService securityService;

    @Autowired
    UserValidator userValidator;

    @Autowired
    RoleService roleService;

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String registration(Model model)
    {
        model.addAttribute("userForm", new User());
        return "users/registration";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String registration(@ModelAttribute("userForm") User userForm, BindingResult bindingResult)
    {
        userValidator.validate(userForm, bindingResult);

        if (bindingResult.hasErrors())
        {
            return "users/registration";
        }
        Role role = roleService.findRoleByName("ROLE_USER");
        HashSet<Role> roles = new HashSet<>();
        roles.add(role);
        userForm.setRoles(roles);
        userService.save(userForm);
        securityService.autologin(userForm.getUsername(), userForm.getPasswordConfirm());
        return "redirect:/";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(HttpServletRequest request)
    {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        securityService.autologin(username, password);

        return "redirect:/";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginForm(Principal principal)
    {
        if (principal == null)
        {
            return "users/login";
        }

        return "redirect:/";

    }

    @RequestMapping(value = "logout")
    public String logout(HttpServletRequest request, HttpServletResponse response)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null)
        {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }

        return "redirect:/";
    }
}

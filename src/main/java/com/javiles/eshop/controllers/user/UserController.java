package com.javiles.eshop.controllers.user;

import com.javiles.eshop.models.Country;
import com.javiles.eshop.models.Role;
import com.javiles.eshop.models.User;
import com.javiles.eshop.services.CountryService;
import com.javiles.eshop.services.RoleService;
import com.javiles.eshop.services.SecurityService;
import com.javiles.eshop.services.UserService;
import com.javiles.eshop.spring.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.HashSet;
import java.util.List;

@Controller
public class UserController
{

    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserValidator userValidator;

    @Autowired
    private RoleService roleService;

    @Autowired
    private CountryService countryService;

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String registration(Model model)
    {
        List<Country> countries = countryService.getAllCountries();
        model.addAttribute("userForm", new User());
        model.addAttribute("countries", countries);
        return "users/registration";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String registration(@ModelAttribute("userForm") User userForm, BindingResult bindingResult, SecurityContextHolderAwareRequestWrapper request, Model model)
    {
        userValidator.validate(userForm, bindingResult);

        if (bindingResult.hasErrors())
        {
            model.addAttribute("countries", countryService.getAllCountries());
            return "users/registration";
        }

        if (request.isUserInRole("ADMIN"))
        {
            Country country = countryService.getCountryByCode(userForm.getCountry().getCountryCode());
            userForm.setCountry(country);
            userService.save(userForm);
            return "redirect:/admin/user";
        }

        Role role = roleService.findRoleByName("ROLE_USER");
        HashSet<Role> roles = new HashSet<>();
        roles.add(role);
        userForm.setRoles(roles);

        Country country = countryService.getCountryByCode(userForm.getCountry().getCountryCode());
        userForm.setCountry(country);

        userService.save(userForm);
        securityService.autologin(userForm.getUsername(), userForm.getPasswordConfirm());
        return "redirect:/";
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public String updateUser(@ModelAttribute("userForm") User userForm, BindingResult bindingResult, SecurityContextHolderAwareRequestWrapper request)
    {
        userValidator.validate(userForm, bindingResult);

        if (bindingResult.hasErrors())
        {
            return "users/registration";
        }

        Country country = countryService.getCountryByCode(userForm.getCountry().getCountryCode());
        userForm.setCountry(country);

        Role role = roleService.findRoleByName("ROLE_USER");
        HashSet<Role> roles = new HashSet<>();
        roles.add(role);
        userForm.setRoles(roles);

        userService.updateUserPasswordAndRoles(userForm);
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

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public String deleteUser(@PathVariable("id") long id, SecurityContextHolderAwareRequestWrapper request)
    {
        if (request.isUserInRole("ADMIN"))
        {
            userService.deleteUserById(id);
            return "redirect:/admin/user";
        }

        User user = userService.findByUserId(id);
        String username = request.getUserPrincipal().getName();
        if (user.getUsername().equals(username))
        {
            userService.deleteUserById(id);
        } else
        {
            return "redirect:/";
        }

        return "redirect:/logout";
    }
}

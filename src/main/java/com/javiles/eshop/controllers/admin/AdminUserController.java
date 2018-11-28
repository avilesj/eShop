package com.javiles.eshop.controllers.admin;

import com.javiles.eshop.models.Country;
import com.javiles.eshop.models.Role;
import com.javiles.eshop.models.User;
import com.javiles.eshop.services.CountryService;
import com.javiles.eshop.services.RoleService;
import com.javiles.eshop.services.UserService;
import com.javiles.eshop.spring.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminUserController
{
    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private CountryService countryService;

    @Autowired
    private UserValidator userValidator;

    //User section methods
    @RequestMapping("/user")
    public String getUserDashboard()
    {
        return "admin/user/adminUserDashboard";
    }

    @RequestMapping("/user/search")
    public String searchUser(@RequestParam("userSearch") String username, Model model)
    {
        List<User> foundUsers = userService.searchUserByUsername(username);
        model.addAttribute("foundUsers", foundUsers);
        return "admin/user/adminUserDashboard";
    }

    @RequestMapping("/user/new")
    public String newUserForm(Model model)
    {
        HashSet<Role> foundRoles = roleService.getAllRoles();
        model.addAttribute("userForm", new User());
        model.addAttribute("countries", countryService.getAllCountries());
        model.addAttribute("userRoles", foundRoles);
        return "admin/user/adminUserNew";
    }

    @RequestMapping("/user/edit/{id}")
    public String editUser(Model model, @PathVariable("id") long id)
    {
        User user = userService.findByUserId(id);
        HashSet<Role> foundRoles = roleService.getAllRoles();
        model.addAttribute("userForm", user);
        model.addAttribute("countries", countryService.getAllCountries());
        model.addAttribute("userRoles", foundRoles);
        return "admin/user/adminUserEdit";
    }

    @RequestMapping(value = "/user/edit/{id}", method = RequestMethod.POST)
    public String updateUser(@ModelAttribute("userForm") User userForm, BindingResult bindingResult, Model model)
    {
        userValidator.validate(userForm, bindingResult);

        if (bindingResult.hasErrors())
        {
            model.addAttribute("countries", countryService.getAllCountries());
            model.addAttribute("userRoles", roleService.getAllRoles());
            return "admin/user/adminUserEdit";
        }

        Country country = countryService.getCountryByCode(userForm.getCountry().getCountryCode());
        userForm.setCountry(country);

        userService.updateUserPasswordAndRoles(userForm);
        userService.updateUserPersonalInformation(userForm);
        return "redirect:/admin/user";
    }
}

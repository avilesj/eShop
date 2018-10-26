package com.hercule.eshop.controllers.admin;

import com.hercule.eshop.models.Role;
import com.hercule.eshop.models.User;
import com.hercule.eshop.services.RoleService;
import com.hercule.eshop.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
        model.addAttribute("userRoles", foundRoles);
        return "admin/user/adminUserNew";
    }

    @RequestMapping("/user/edit/{id}")
    public String editUser(Model model, @PathVariable("id") long id)
    {
        User user = userService.findByUserId(id);
        HashSet<Role> foundRoles = roleService.getAllRoles();
        model.addAttribute("userForm", user);
        model.addAttribute("userRoles", foundRoles);
        return "admin/user/adminUserEdit";
    }
}

package com.hercule.eshop.controllers;


import com.hercule.eshop.models.Product;
import com.hercule.eshop.models.Role;
import com.hercule.eshop.models.User;
import com.hercule.eshop.services.ProductService;
import com.hercule.eshop.services.RoleService;
import com.hercule.eshop.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashSet;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController
{
    @Autowired
    UserService userService;

    @Autowired
    RoleService roleService;

    @Autowired
    ProductService productService;

    @RequestMapping("")
    public String getAdminIndex()
    {
        return "admin/adminIndex";
    }

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

    //Product section methods
    @RequestMapping("/product")
    public String getProductDashboard()
    {
        return "admin/product/adminProductDashboard";
    }

    @RequestMapping("/product/search")
    public String searchProduct(@RequestParam("productSearch") String productName, Model model)
    {
        List<Product> foundProducts = productService.findProductsBySearchTerms(productName);
        model.addAttribute("foundProducts", foundProducts);
        return "admin/product/adminProductDashboard";
    }

    @RequestMapping("/product/new")
    public String newProductForm(Model model)
    {
        model.addAttribute("productForm", new Product());
        return "admin/product/adminProductNew";
    }

    @RequestMapping("/product/edit/{id}")
    public String editProduct(Model model, @PathVariable("id") long id)
    {
        Product product = productService.findProductById(id);
        model.addAttribute("productForm", product);
        return "admin/product/adminProductEdit";
    }


    @RequestMapping(value = {"/product/save"}, method = RequestMethod.POST)
    public String saveProduct(final Product product, Model model)
    {
        productService.saveProduct(product);
        model.addAttribute("productForm", product);
        return "redirect:/admin/product/edit/" + product.getId();
    }

    @RequestMapping(value = {"/product/edit/{id}"}, method = RequestMethod.POST)
    public String saveEditedProduct(final Product product, Model model)
    {
        productService.updateProduct(product);
        return "redirect:/admin/product/";
    }

    @RequestMapping(value = "/product/delete/{id}", method = RequestMethod.POST)
    public String deleteProduct(Model model, @PathVariable("id") long id)
    {
        productService.deleteProduct(id);
        return "admin/product/adminProductDashboard";
    }



}

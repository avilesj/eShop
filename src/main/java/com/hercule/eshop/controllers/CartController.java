package com.hercule.eshop.controllers;

import com.hercule.eshop.models.Cart;
import com.hercule.eshop.models.Product;
import com.hercule.eshop.models.User;
import com.hercule.eshop.services.CartService;
import com.hercule.eshop.services.ProductService;
import com.hercule.eshop.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
@RequestMapping("/Cart")
public class CartController
{
    @Autowired
    private ProductService productService;
    @Autowired
    private UserService userService;
    @Autowired
    private CartService cartService;

    @RequestMapping(value = "/add/{id}")
    public String addProductToCart(@PathVariable("id") long id, Principal principal, @RequestParam(value = "amt") long amount)
    {
        if (principal == null)
        {
            return "/";
        }

        User user = userService.findByUsername(principal.getName());
        Cart cart = cartService.findCartByUserId(user);
        Product product = productService.findProductById(id);
        cartService.addItemToCart(cart, product, amount);
        return "redirect:/";
    }
}

package com.hercule.eshop.controllers;

import com.hercule.eshop.models.CartItem;
import com.hercule.eshop.models.Product;
import com.hercule.eshop.models.User;
import com.hercule.eshop.services.CartService;
import com.hercule.eshop.services.ProductService;
import com.hercule.eshop.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

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
    public String addProductToCart(@PathVariable("id") long id, Principal principal, @ModelAttribute("cartItem") CartItem cartItem)
    {
        if (principal == null)
        {
            return "/";
        }

        User user = userService.findByUsername(principal.getName());
        Product product = productService.findProductById(id);
        cartItem.setCart(cartService.findCartByUserId(user));
        cartItem.setProduct(product);
        cartService.addItemToCart(cartItem);
        return "redirect:/";
    }
}

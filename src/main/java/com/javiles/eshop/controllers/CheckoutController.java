package com.javiles.eshop.controllers;

import com.javiles.eshop.models.Cart;
import com.javiles.eshop.models.User;
import com.javiles.eshop.services.CartService;
import com.javiles.eshop.services.OrderService;
import com.javiles.eshop.services.UserService;
import com.javiles.eshop.stripe.services.StripeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
public class CheckoutController
{
    @Autowired
    private UserService userService;

    @Autowired
    private CartService cartService;

    @Autowired
    private StripeService stripeService;

    @Autowired
    private OrderService orderService;

    @RequestMapping("/checkout")
    public String getCartSummary(Model model, Principal principal)
    {
        User user = userService.findByUsername(principal.getName());

        Cart cart = cartService.findCartByUserId(user.getId());
        if (cart.getSize() == 0)
        {
            return "redirect:/cart";
        }

        model.addAttribute("cart", cart);
        //model.addAttribute("cartItems", cart.getCartItem());

        return "checkout/orderSummary";
    }
}

package com.javiles.eshop.controllers.user;

import com.javiles.eshop.models.Cart;
import com.javiles.eshop.models.CartItem;
import com.javiles.eshop.models.User;
import com.javiles.eshop.services.CartService;
import com.javiles.eshop.services.OrderService;
import com.javiles.eshop.services.UserService;
import com.javiles.eshop.stripe.models.StripeCustomer;
import com.javiles.eshop.stripe.services.StripeService;
import com.stripe.exception.StripeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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

        StripeCustomer stripeCustomer = stripeService.getCustomerByUserId(user.getId());

        model.addAttribute("cart", cart);
        model.addAttribute("stripeCustomer", stripeCustomer);

        return "checkout/orderSummary";
    }

    @RequestMapping(value = "/checkout", method = RequestMethod.POST)
    public String createOrder(Principal principal)
    {
        User user = userService.findByUsername(principal.getName());
        StripeCustomer stripeCustomer = stripeService.getCustomerByUserId(user.getId());
        if (stripeCustomer == null)
        {
            return "redirect:/cart";
        }

        Cart cart = cartService.findCartByUserId(user.getId());
        double total = cart.getCartItem().stream().mapToDouble(CartItem::getTotal).sum();
        try
        {
            stripeService.makePayment(user, total);
            orderService.createOrder(cart);
        } catch (StripeException e)
        {
            e.printStackTrace();
        }
        return "redirect:/";
    }
}

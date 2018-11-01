package com.javiles.eshop.controllers.user;

import com.javiles.eshop.models.Cart;
import com.javiles.eshop.models.CartItem;
import com.javiles.eshop.models.Product;
import com.javiles.eshop.models.User;
import com.javiles.eshop.services.CartService;
import com.javiles.eshop.services.ProductService;
import com.javiles.eshop.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/cart")
public class CartController
{
    @Autowired
    private ProductService productService;
    @Autowired
    private UserService userService;
    @Autowired
    private CartService cartService;

    @RequestMapping(value = "")
    public String showCart(Principal principal, Model model)
    {
        if (principal == null)
        {
            return "/";
        }

        User user = userService.findByUsername(principal.getName());
        Cart cart = cartService.findCartByUserId(user.getId());
        List<CartItem> allCartItems = cart.getCartItem();
        model.addAttribute("allCartItems", allCartItems);
        return "cart/cart";
    }

    @RequestMapping(value = "/add/{id}")
    public String addProductToCart(@PathVariable("id") long id, Principal principal, @ModelAttribute("cartItem") CartItem cartItem)
    {
        if (principal == null)
        {
            return "redirect:/";
        }

        User user = userService.findByUsername(principal.getName());
        Product product = productService.findProductById(id);
        cartItem.setCart(cartService.findCartByUserId(user.getId()));
        cartItem.setProduct(product);
        cartItem.setPrice(product.getPrice());
        cartService.addItemToCart(cartItem);
        return "redirect:/cart/";
    }

    @RequestMapping(value = "/delete/{id}")
    public String removeProductFromCart(@PathVariable("id") long id, Principal principal, @ModelAttribute("cartItem") CartItem cartItem)
    {
        if (principal == null)
        {
            return "redirect:/";
        }


        User user = userService.findByUsername(principal.getName());
        Cart cart = cartService.findCartByUserId(user.getId());
        cartService.removeItemFromCart(cart, cartItem);
        return "redirect:/cart/";
    }
}

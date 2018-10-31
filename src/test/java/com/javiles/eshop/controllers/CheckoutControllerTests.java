package com.javiles.eshop.controllers;


import com.javiles.eshop.models.CartItem;
import com.javiles.eshop.models.Product;
import com.javiles.eshop.models.User;
import com.javiles.eshop.services.CartService;
import com.javiles.eshop.services.ProductService;
import com.javiles.eshop.services.UserService;
import com.javiles.eshop.stripe.services.StripeCustomerService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("dev")
public class CheckoutControllerTests
{
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductService productService;

    @Autowired
    private CartService cartService;

    @Autowired
    private UserService userService;

    @Autowired
    private StripeCustomerService stripeCustomerService;

    private Product product;
    private User user;

    @Before
    public void initialize()
    {
        this.user = new User();
        this.user.setUsername("michaelscott");
        this.user.setPassword("321321");
        userService.save(this.user);

        this.product = new Product();
        this.product.setName("Butter");
        this.product.setPrice(20.00);
        this.product.setDescription("Just butter");
        productService.saveProduct(this.product);
    }

    @After
    public void terminate()
    {
        userService.deleteUserById(this.user.getId());
        productService.deleteProduct(this.product.getId());
    }

    @Test
    @WithMockUser(username = "michaelscott", roles = {"ADMIN"})
    public void shouldAddItemToCartAndProceedToCheckoutWithoutPaymentMethod() throws Exception
    {
        CartItem cartItem = new CartItem();
        cartItem.setQuantity(1);
        cartItem.setProduct(this.product);
        cartItem.setCart(cartService.findCartByUserId(this.user.getId()));

        this.mockMvc.perform(get("/products/search?name=" + this.product.getName())).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(this.product.getName())));

        this.mockMvc.perform(get("/products/" + this.product.getId())).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(this.product.getName())));

        this.mockMvc.perform(get("/cart/add/" + this.product.getId()).flashAttr("cartItem", cartItem))
                .andDo(print())
                .andExpect(redirectedUrl("/cart/"))
                .andExpect(status().is3xxRedirection());


        this.mockMvc.perform(get("/checkout"))
                .andDo(print())
                .andExpect(content().string(containsString(this.product.getName())))
                .andExpect(content().string(containsString("No payment method found.")))
                .andExpect(content().string(containsString("Pay with stripe")));

    }

    @Test
    @WithMockUser(username = "michaelscott", roles = {"ADMIN"})
    public void shouldAddItemToCartAndProceedToCheckoutWithPaymentMethod() throws Exception
    {
        //Create cart item for test
        CartItem cartItem = new CartItem();
        cartItem.setQuantity(1);
        cartItem.setProduct(this.product);
        cartItem.setCart(cartService.findCartByUserId(this.user.getId()));

        //Create Stripe Customer for test
        stripeCustomerService.saveStripeCustomer(this.user, "key");

        //Begin test
        this.mockMvc.perform(get("/products/search?name=" + this.product.getName())).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(this.product.getName())));

        this.mockMvc.perform(get("/products/" + this.product.getId())).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(this.product.getName())));

        this.mockMvc.perform(get("/cart/add/" + this.product.getId()).flashAttr("cartItem", cartItem))
                .andDo(print())
                .andExpect(redirectedUrl("/cart/"))
                .andExpect(status().is3xxRedirection());


        this.mockMvc.perform(get("/checkout"))
                .andDo(print())
                .andExpect(content().string(containsString(this.product.getName())))
                .andExpect(content().string(containsString("Your stripe credentials will be used for this transaction.")));

    }
}

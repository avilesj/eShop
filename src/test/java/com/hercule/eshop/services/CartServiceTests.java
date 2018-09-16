package com.hercule.eshop.services;

import com.hercule.eshop.models.Cart;
import com.hercule.eshop.models.CartItem;
import com.hercule.eshop.models.Product;
import com.hercule.eshop.models.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("dev")
public class CartServiceTests
{
    @Autowired
    private CartService cartService;

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    private User user;
    private Product product;

    @Before
    public void initialize()
    {
        this.user = new User();
        this.user.setUsername("javiles");
        this.user.setPassword("321321");
        userService.save(this.user);

        this.product = new Product();
        this.product.setName("Pizza");
        this.product.setDescription("True Napolitan Pizza");
        this.product.setPrice(8.00);
        productService.saveProduct(this.product);
    }

    @Test
    public void validateCartCreationOnUserCreation()
    {
        User dbUser = userService.findByUsername(this.user.getUsername());
        assertNotNull(cartService.findCartByUserId(user));
    }

    @Test
    public void addsItemToCart()
    {
        int AMOUNT = 2;
        Cart cart = cartService.findCartByUserId(this.user);

        cartService.addItemToCart(cart, this.product, AMOUNT);

        cart = cartService.findCartByUserId(this.user);
        assertEquals(AMOUNT, cart.getSize());



    }

    @Test
    public void validatesRemovalOfCartItem()
    {
        Cart cart = cartService.findCartByUserId(this.user);

        cartService.addItemToCart(cart, this.product, 5);
        List<CartItem> cartItem = cart.getCartItem();

        cartService.removeItemFromCart(cart, cartItem.get(0));

        assertNull(cart.getCartItem());


    }


}

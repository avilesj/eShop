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

import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("dev")
public class CartItemServiceTests
{
    @Autowired
    private UserService userService;

    @Autowired
    private CartService cartService;

    @Autowired
    private CartItemService cartItemService;

    @Autowired
    private ProductService productService;

    private Cart cart;
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
        this.product.setName("Shirt");
        this.product.setPrice(25.00);
        this.product.setDescription("Description");
        productService.saveProduct(this.product);

    }

    @Test
    public void validatesCartItemInsertion()
    {
        CartItem cartItem = new CartItem();
        Cart cart = cartService.findCartByUserId(this.user);
        cartItem.setCart(cart);
        cartItem.setQuantity(2);
        cartItem.setProduct(this.product);
        cartItemService.saveCartItem(cartItem);

        List<CartItem> cartItems = cartItemService.getAllItemsFromCart(cart);

        assertNotNull(cartItems);
    }
}

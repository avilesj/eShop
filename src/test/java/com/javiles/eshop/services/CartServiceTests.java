package com.javiles.eshop.services;

import com.javiles.eshop.models.Cart;
import com.javiles.eshop.models.CartItem;
import com.javiles.eshop.models.Product;
import com.javiles.eshop.models.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@ActiveProfiles("dev")
public class CartServiceTests
{
    @Autowired
    private CartService cartService;

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    @PersistenceContext
    private EntityManager entityManager;

    private User user;
    private Product product;
    private CartItem cartItem;


    @Before
    public void initialize()
    {
        this.user = new User();
        this.user.setUsername("javiles");
        this.user.setPassword("321321");

        this.product = new Product();
        this.product.setName("Pizza");
        this.product.setDescription("True Napolitan Pizza");
        this.product.setPrice(8.00);

        this.cartItem = new CartItem();
    }

    @Test
    public void validateCartCreationOnUserCreation()
    {
        User user = new User();
        user.setUsername("javiles");
        user.setPassword("321321");
        userService.save(user);
        entityManager.flush();
        User dbUser = userService.findByUsername(user.getUsername());
        Cart cart = cartService.findCartByUserId(dbUser.getId());
        assertNotNull(cart);
    }

    @Test
    public void addsItemToCart()
    {
        userService.save(user);
        productService.saveProduct(product);
        entityManager.flush();
        entityManager.clear();

        int AMOUNT = 2;


        this.cartItem.setCart(cartService.findCartByUserId(userService.findByUsername("javiles").getId()));
        this.cartItem.setQuantity(AMOUNT);
        this.cartItem.setProduct(this.product);
        cartService.addItemToCart(this.cartItem);
        entityManager.flush();
        entityManager.clear();
        Cart cart2 = cartService.findCartByUserId(userService.findByUsername("javiles").getId());
        assertEquals(AMOUNT, cart2.getSize());
    }

    @Test
    public void validatesRemovalOfCartItem()
    {
        userService.save(user);
        entityManager.flush();
        entityManager.clear();
        productService.saveProduct(product);
        entityManager.flush();
        entityManager.clear();

        this.cartItem.setCart(cartService.findCartByUserId(userService.findByUsername("javiles").getId()));
        this.cartItem.setQuantity(5);
        this.cartItem.setProduct(this.product);
        cartService.addItemToCart(this.cartItem);
        entityManager.flush();
        entityManager.clear();

        Cart dbCart = cartService.findCartByUserId(userService.findByUsername("javiles").getId());

        List<CartItem> cartItem = dbCart.getCartItem();
        cartService.removeItemFromCart(dbCart, cartItem.get(0));
        entityManager.flush();
        entityManager.clear();

        Cart cart2 = cartService.findCartByUserId(user.getId());
        assertEquals(0, cart2.getCartItem().size());
    }

    @Test
    public void shouldEmptyCart()
    {
        userService.save(user);
        entityManager.flush();
        entityManager.clear();
        productService.saveProduct(product);
        entityManager.flush();
        entityManager.clear();

        this.cartItem.setCart(cartService.findCartByUserId(userService.findByUsername("javiles").getId()));
        this.cartItem.setQuantity(5);
        this.cartItem.setProduct(this.product);
        cartService.addItemToCart(this.cartItem);

        CartItem cartItem1 = new CartItem();
        cartItem1.setCart(cartService.findCartByUserId(userService.findByUsername("javiles").getId()));
        cartItem1.setQuantity(5);
        cartItem1.setProduct(this.product);
        cartService.addItemToCart(this.cartItem);
        entityManager.flush();
        entityManager.clear();

        Cart dbCart = cartService.findCartByUserId(userService.findByUsername("javiles").getId());

        cartService.emptyCart(dbCart);
        entityManager.flush();
        entityManager.clear();

        Cart cart2 = cartService.findCartByUserId(user.getId());
        assertEquals(0, cart2.getCartItem().size());
    }

}

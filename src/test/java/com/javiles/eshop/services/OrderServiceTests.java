package com.javiles.eshop.services;

import com.javiles.eshop.models.*;
import com.javiles.eshop.repositories.OrderItemRepository;
import com.javiles.eshop.repositories.OrderRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("dev")
public class OrderServiceTests
{
    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @Autowired
    private CartService cartService;

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    private User user;

    private Product product1;
    private Product product2;

    @Before
    public void init()
    {
        this.user = new User();
        this.user.setUsername("michaelscott");
        this.user.setPassword("321321");
        userService.save(this.user);

        this.product1 = new Product();
        this.product1.setName("Dunder Miffling Paper 8 by 11");
        this.product1.setDescription("The best paper on the region");
        this.product1.setPrice(5.00);

        this.product2 = new Product();
        this.product2.setName("Staples Paper 8 by 11");
        this.product2.setDescription("Not the best paper on the region");
        this.product2.setPrice(11.00);

        productService.saveProduct(this.product1);
        productService.saveProduct(this.product2);

        CartItem cartItem1 = new CartItem();
        cartItem1.setCart(cartService.findCartByUserId(this.user));
        cartItem1.setProduct(this.product1);
        cartItem1.setQuantity(2);

        CartItem cartItem2 = new CartItem();
        cartItem2.setCart(cartService.findCartByUserId(this.user));
        cartItem2.setProduct(this.product2);
        cartItem2.setQuantity(5);

        cartService.addItemToCart(cartItem1);
        cartService.addItemToCart(cartItem2);
    }

    @After
    public void terminate()
    {
        orderItemRepository.deleteAll();
        orderRepository.deleteAll();
        userService.deleteUserById(this.user.getId());
        productService.deleteProduct(this.product1.getId());
        productService.deleteProduct(this.product2.getId());
    }

    @Test
    public void shouldMakeOrderWithCartItemsAndEmptyCart()
    {
        orderService.createOrder(cartService.findCartByUserId(this.user));
        Cart cart = cartService.findCartByUserId(this.user);
        Order order = orderService.getOrderByUserId(user.getId());

        assertEquals(0, cart.getCartItem().size());
        assertNotNull(order);
        assertEquals(2, order.getSize());
        assertEquals("PENDING", order.getStatus());
    }

    @Test
    public void shouldChangeOrderStatusToComplete()
    {
        orderService.createOrder(cartService.findCartByUserId(this.user));
        Order order = orderService.getOrderByUserId(user.getId());

        assertEquals("PENDING", order.getStatus());

        orderService.completeOrderByUserId(this.user.getId());

        order = orderService.getOrderByUserId(user.getId());

        assertEquals("COMPLETED", order.getStatus());
    }

    @Test
    public void shouldChangeOrderStatusToCancelled()
    {
        orderService.createOrder(cartService.findCartByUserId(this.user));
        Order order = orderService.getOrderByUserId(user.getId());

        assertEquals("PENDING", order.getStatus());

        orderService.cancelOrderByUserId(this.user.getId());

        order = orderService.getOrderByUserId(user.getId());

        assertEquals("CANCELLED", order.getStatus());
    }

    @Test
    public void shouldNotCancelCompletedOrder()
    {
        orderService.createOrder(cartService.findCartByUserId(this.user));
        orderService.completeOrderByUserId(this.user.getId());

        Order order = orderService.getOrderByUserId(user.getId());

        assertEquals("COMPLETED", order.getStatus());

        orderService.cancelOrderByUserId(this.user.getId());

        order = orderService.getOrderByUserId(user.getId());

        assertNotEquals("CANCELLED", order.getStatus());
    }

    @Test
    public void shouldNotCompleteCancelledOrder()
    {
        orderService.createOrder(cartService.findCartByUserId(this.user));
        orderService.cancelOrderByUserId(this.user.getId());

        Order order = orderService.getOrderByUserId(user.getId());

        assertEquals("CANCELLED", order.getStatus());

        orderService.completeOrderByUserId(this.user.getId());

        order = orderService.getOrderByUserId(user.getId());

        assertNotEquals("COMPLETED", order.getStatus());
    }

}

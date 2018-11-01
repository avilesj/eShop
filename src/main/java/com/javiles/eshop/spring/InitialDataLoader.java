package com.javiles.eshop.spring;

import com.javiles.eshop.models.*;
import com.javiles.eshop.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;

@Component
public class InitialDataLoader implements ApplicationListener<ContextRefreshedEvent>
{

    private static final String ADMIN_USERNAME = "administrator";
    private static final String ADMIN_PASSWORD = "321321";
    private boolean alreadySetup = false;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private ProductService productService;

    @Autowired
    private CartService cartService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private Environment environment;


    @Override
    public void onApplicationEvent(ContextRefreshedEvent event)
    {

        if (alreadySetup)
        {
            return;
        }

        createRoleIfNotFound("ROLE_ADMIN");
        createRoleIfNotFound("ROLE_USER");

        User user = userService.findByUsername(ADMIN_USERNAME);
        if (user == null)
        {
            user = new User();
            user.setUsername(ADMIN_USERNAME);
            user.setPassword(ADMIN_PASSWORD);
            HashSet<Role> roles = roleService.getAllRoles();
            user.setRoles(roles);
            userService.save(user);
        }

        for (String env : environment.getActiveProfiles())
        {
            if (env.equals("testing"))
            {
                createTestProducts();
                createTestOrders();
            }
        }


        alreadySetup = true;


    }

    @Transactional
    private void createRoleIfNotFound(String name)
    {
        Role role;

        HashSet<Role> roles = roleService.getAllRoles();
        if (!roles.contains(name))
        {
            role = new Role();
            role.setName(name);
            roleService.saveRole(role);
        }
    }

    @Transactional
    private void createTestProducts()
    {
        Product redShirt = new Product();
        Product blueShirt = new Product();
        Product greenShirt = new Product();
        double shirtPrice = 25.00;
        String shirtDescription = "Generic shirt";

        redShirt.setName("Red Shirt");
        redShirt.setPrice(shirtPrice);
        redShirt.setDescription(shirtDescription);

        blueShirt.setName("Blue Shirt");
        blueShirt.setPrice(shirtPrice);
        blueShirt.setDescription(shirtDescription);

        greenShirt.setName("Green Shirt");
        greenShirt.setPrice(shirtPrice);
        greenShirt.setDescription(shirtDescription);

        productService.saveProduct(redShirt);
        productService.saveProduct(blueShirt);
        productService.saveProduct(greenShirt);
    }

    private void createTestOrders()
    {
        User user = userService.findByUsername(ADMIN_USERNAME);
        Cart cart = cartService.findCartByUserId(user.getId());

        CartItem cartItem = new CartItem();
        cartItem.setProduct(productService.findProductByName("Green Shirt"));
        cartItem.setQuantity(1);
        cartItem.setPrice(cartItem.getProduct().getPrice());
        cartItem.setTotal(cartItem.getProduct().getPrice() * 3);
        cartItem.setCart(cart);

        cartService.addItemToCart(cartItem);

        orderService.createOrder(cartService.findCartByUserId(user.getId()));
    }
}

package com.javiles.eshop.services;

import com.javiles.eshop.models.Cart;
import com.javiles.eshop.models.User;
import com.javiles.eshop.repositories.RoleRepository;
import com.javiles.eshop.repositories.UserRepository;
import com.javiles.eshop.stripe.models.StripeCustomer;
import com.javiles.eshop.stripe.services.StripeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;


public class UserServiceImpl implements UserService
{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private CartService cartService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private StripeService stripeService;

    @Override
    public void save(User user)
    {
        user.setUsername(user.getUsername().toLowerCase());
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);

        Cart userCart = new Cart();
        userCart.setUser(user);
        cartService.saveCart(userCart);
    }

    @Override
    public User findByUsername(String username)
    {
        return userRepository.findByUsername(username);
    }

    @Override
    public User findByUserId(long id)
    {
        return userRepository.findOneById(id);
    }

    @Override
    public void deleteUserById(long id)
    {
        User user = findByUserId(id);
        Cart cart = cartService.findCartByUserId(user.getId());
        cartService.deleteCart(cart);

        StripeCustomer stripeCustomer = stripeService.getCustomerByUserId(user.getId());
        if (stripeCustomer != null)
        {
            stripeService.deleteCustomer(user);
        }

        userRepository.delete(user);
    }

    @Override
    public List<User> searchUserByUsername(String name)
    {
        return userRepository.findByUsernameContaining(name.toLowerCase());
    }

    @Override
    public void updateUserPasswordAndRoles(User user)
    {
        user.setUsername(user.getUsername().toLowerCase());
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

        User dbUser = findByUserId(user.getId());
        dbUser.setUsername(user.getUsername());
        dbUser.setPassword(user.getPassword());
        dbUser.setRoles(user.getRoles());

        userRepository.save(dbUser);

    }

    @Override
    public void updateUserPersonalInformation(User user)
    {
        User dbUser = findByUsername(user.getUsername());

        dbUser.setCountry(user.getCountry());
        dbUser.setAddress(user.getAddress());
        dbUser.setEmail(user.getEmail());
        dbUser.setCity(user.getCity());
        dbUser.setZipCode(user.getZipCode());
        dbUser.setPhoneNumber(user.getPhoneNumber());

        userRepository.save(dbUser);
    }

    @Override
    public void updateUserPassword(User user)
    {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        User dbUser = findByUsername(user.getUsername());
        dbUser.setPassword(user.getPassword());
        userRepository.save(dbUser);
    }


}

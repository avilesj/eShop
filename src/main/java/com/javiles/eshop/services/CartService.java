package com.javiles.eshop.services;

import com.javiles.eshop.models.Cart;
import com.javiles.eshop.models.CartItem;
import com.javiles.eshop.models.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public interface CartService
{
    void saveCart(Cart cart);

    void addItemToCart(CartItem cartItem);

    void removeItemFromCart(Cart cart, CartItem cartItem);

    void deleteCart(Cart cart);

    Cart findCartByUserId(User user);

    void emptyCart(Cart cart);

}

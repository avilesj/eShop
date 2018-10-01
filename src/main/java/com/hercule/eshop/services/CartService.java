package com.hercule.eshop.services;

import com.hercule.eshop.models.Cart;
import com.hercule.eshop.models.CartItem;
import com.hercule.eshop.models.Product;
import com.hercule.eshop.models.User;

public interface CartService
{
    void saveCart(Cart cart);

    void addItemToCart(Cart cart, Product product, long amount);

    void removeItemFromCart(CartItem cartItem);

    Cart findCartByUserId(User user);

}

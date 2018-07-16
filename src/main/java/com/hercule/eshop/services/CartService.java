package com.hercule.eshop.services;

import com.hercule.eshop.models.Cart;
import com.hercule.eshop.models.Product;
import com.hercule.eshop.models.User;

public interface CartService
{
    void saveCart(Cart cart);

    void addItemToCart(User user, Product product);

    void removeItemFromCart(User user, Product product);

    Cart getUserCart(User user);

}

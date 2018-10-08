package com.hercule.eshop.services;

import com.hercule.eshop.models.Cart;
import com.hercule.eshop.models.CartItem;

import java.util.List;

public interface CartItemService
{
    void saveCartItem(CartItem cartItem);

    void deleteCartItem(CartItem cartItem);

    void purgeCartItems(Cart cart);

    List<CartItem> getAllItemsFromCart(Cart cart);

}

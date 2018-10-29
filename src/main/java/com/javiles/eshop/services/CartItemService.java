package com.javiles.eshop.services;

import com.javiles.eshop.models.Cart;
import com.javiles.eshop.models.CartItem;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface CartItemService
{
    void saveCartItem(CartItem cartItem);

    void deleteCartItem(CartItem cartItem);

    void deleteAllCartItemsByCart(Cart cart);

    List<CartItem> getAllItemsFromCart(Cart cart);

}

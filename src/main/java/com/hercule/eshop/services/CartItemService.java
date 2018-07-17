package com.hercule.eshop.services;

import com.hercule.eshop.models.CartItem;

public interface CartItemService
{
    void saveCartItem(CartItem cartItem);

    void deleteCartItem(CartItem cartItem);

}

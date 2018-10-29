package com.javiles.eshop.services;

import com.javiles.eshop.models.Cart;

public interface OrderService
{
    void createOrder(Cart cart);
}

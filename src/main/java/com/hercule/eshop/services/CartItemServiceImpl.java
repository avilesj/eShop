package com.hercule.eshop.services;

import com.hercule.eshop.models.CartItem;
import com.hercule.eshop.repositories.CartItemRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class CartItemServiceImpl implements CartItemService
{
    @Autowired
    CartItemRepository cartItemRepository;

    @Override
    public void saveCartItem(CartItem cartItem)
    {
        cartItemRepository.save(cartItem);
    }

    @Override
    public void deleteCartItem(CartItem cartItem)
    {
        cartItemRepository.delete(cartItem);
    }
}

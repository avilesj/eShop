package com.hercule.eshop.services;

import com.hercule.eshop.models.Cart;
import com.hercule.eshop.models.CartItem;
import com.hercule.eshop.repositories.CartItemRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

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

    @Override
    public List<CartItem> getAllItemsFromCart(Cart cart)
    {
        return cartItemRepository.findByCart(cart);
    }


}

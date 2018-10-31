package com.javiles.eshop.services;

import com.javiles.eshop.models.Cart;
import com.javiles.eshop.models.CartItem;
import com.javiles.eshop.repositories.CartItemRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class CartItemServiceImpl implements CartItemService
{
    @Autowired
    private CartItemRepository cartItemRepository;

    @Override
    public void saveCartItem(CartItem cartItem)
    {
        cartItem.setTotal(cartItem.getPrice() * cartItem.getQuantity());
        cartItemRepository.save(cartItem);
    }

    @Override
    public void deleteCartItem(CartItem cartItem)
    {
        cartItemRepository.delete(cartItem);
    }

    @Override
    public void deleteAllCartItemsByCart(Cart cart)
    {
        cartItemRepository.deleteByCartId(cart.getId());
    }

    @Override
    public List<CartItem> getAllItemsFromCart(Cart cart)
    {
        return cartItemRepository.findByCart(cart);
    }

}

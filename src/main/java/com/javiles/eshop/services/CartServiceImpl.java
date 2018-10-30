package com.javiles.eshop.services;

import com.javiles.eshop.models.Cart;
import com.javiles.eshop.models.CartItem;
import com.javiles.eshop.repositories.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class CartServiceImpl implements CartService
{
    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemService cartItemService;

    @Override
    public void saveCart(Cart cart)
    {
        cartRepository.save(cart);
    }

    @Override
    public void addItemToCart(CartItem cartItem)
    {
        cartItemService.saveCartItem(cartItem);
    }

    @Override
    public void removeItemFromCart(Cart cart, CartItem cartItem)
    {
        boolean cartItemBelongsToCart;

        List<CartItem> items = cart.getCartItem();
        cartItemBelongsToCart = items.stream().filter(i -> i.getId() == cartItem.getId()).findFirst().isPresent();

        if (cartItemBelongsToCart)
        {
            cartItemService.deleteCartItem(cartItem);
        }
    }

    @Override
    public void deleteCart(Cart cart)
    {
        cartItemService.deleteAllCartItemsByCart(cart);
        cartRepository.delete(cart);
    }

    @Override
    public Cart findCartByUserId(long userId)
    {
        return cartRepository.findByUserId(userId);
    }

    @Override
    public void emptyCart(Cart cart)
    {
        cartItemService.deleteAllCartItemsByCart(cart);
    }

}

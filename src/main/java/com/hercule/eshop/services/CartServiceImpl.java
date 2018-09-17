package com.hercule.eshop.services;

import com.hercule.eshop.models.Cart;
import com.hercule.eshop.models.CartItem;
import com.hercule.eshop.models.Product;
import com.hercule.eshop.models.User;
import com.hercule.eshop.repositories.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;

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
    public void addItemToCart(Cart cart, Product product, int amount)
    {
        CartItem cartItem = new CartItem();
        cartItem.setCart(cart);
        cartItem.setProduct(product);
        cartItem.setQuantity(amount);
        cartItemService.saveCartItem(cartItem);

    }

    @Override
    public void removeItemFromCart(CartItem cartItem)
    {
        cartItemService.deleteCartItem(cartItem);
    }

    @Override
    public Cart findCartByUserId(User user)
    {
        return cartRepository.findByUserId(user.getId());
    }

}

package com.hercule.eshop.services;

import com.hercule.eshop.models.Cart;
import com.hercule.eshop.models.Product;
import com.hercule.eshop.models.User;
import com.hercule.eshop.repositories.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class CartServiceImpl implements CartService
{
    @Autowired
    CartRepository cartRepository;

    @Override
    public void saveCart(Cart cart)
    {

    }

    @Override
    public void addItemToCart(User user, Product product)
    {

    }

    @Override
    public void removeItemFromCart(User user, Product product)
    {

    }

    @Override
    public Cart getUserCart(User user)
    {
        return cartRepository.findByUserId(user);
    }
}

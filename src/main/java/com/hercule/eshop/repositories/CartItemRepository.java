package com.hercule.eshop.repositories;

import com.hercule.eshop.models.Cart;
import com.hercule.eshop.models.CartItem;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CartItemRepository extends CrudRepository<CartItem, Long>
{
    List<CartItem> findByCart(Cart cart);
}

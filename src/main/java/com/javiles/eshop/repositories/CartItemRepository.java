package com.javiles.eshop.repositories;

import com.javiles.eshop.models.Cart;
import com.javiles.eshop.models.CartItem;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CartItemRepository extends CrudRepository<CartItem, Long>
{
    List<CartItem> findByCart(Cart cart);

    void deleteByCartId(long cartId);
}

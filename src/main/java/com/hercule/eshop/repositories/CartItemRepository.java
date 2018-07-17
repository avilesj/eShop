package com.hercule.eshop.repositories;

import com.hercule.eshop.models.CartItem;
import org.springframework.data.repository.CrudRepository;

public interface CartItemRepository extends CrudRepository<CartItem, Long>
{
}

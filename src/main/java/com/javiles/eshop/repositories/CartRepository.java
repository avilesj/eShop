package com.javiles.eshop.repositories;

import com.javiles.eshop.models.Cart;
import org.springframework.data.repository.CrudRepository;

public interface CartRepository extends CrudRepository<Cart, Long>
{
    Cart findByUserId(long id);
}

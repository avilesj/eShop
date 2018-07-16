package com.hercule.eshop.repositories;

import com.hercule.eshop.models.Cart;
import com.hercule.eshop.models.User;
import org.springframework.data.repository.CrudRepository;

public interface CartRepository extends CrudRepository<Cart, Long>
{
    Cart findByUserId(User user);
}
